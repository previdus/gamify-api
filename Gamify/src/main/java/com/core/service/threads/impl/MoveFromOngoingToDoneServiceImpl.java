package com.core.service.threads.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.core.domain.knockout.GameInstance;
import com.core.manager.CommonQueueManager;

import com.core.service.threads.MoveFromOngoingToDoneService;

@Service("moveFromOngoingToDoneService")
public class MoveFromOngoingToDoneServiceImpl implements  MoveFromOngoingToDoneService{

	private static final Logger log = LoggerFactory
			.getLogger(MoveFromOngoingToDoneServiceImpl.class);
	public void run() {

		log.info("4) periodicTaskToMoveFromOngoingToDone");

		// from ongoing to done
		try {
			for (Long gameInstanceId : CommonQueueManager.ongoingGames
					.keySet()) {
				GameInstance gi = CommonQueueManager.ongoingGames
						.get(gameInstanceId);
				if (areThereNoPlayersLeftInTheGameOrThereIsAGameWinner(gi) && gi.isGameStillActive()) {
					CommonQueueManager.endGame(gi);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private boolean areThereNoPlayersLeftInTheGameOrThereIsAGameWinner(GameInstance gi) {
		
		return gi.getPlayers().size() <= 1 || (gi.getGameWinner() != null);
	}
	


}
