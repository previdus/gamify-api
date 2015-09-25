package com.core.service.threads.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.domain.knockout.GameInstance;
import com.core.manager.GameQueueManager;
import com.core.manager.QuestionManager;
import com.core.service.threads.MoveFromReadyToOngoingQueueService;


@Service("moveFromReadyToOngoingQueueService")
public class MoveFromReadyToOngoingQueueServiceImpl implements 
 MoveFromReadyToOngoingQueueService {
	
	private static final Logger log = LoggerFactory
			.getLogger(MoveFromReadyToOngoingQueueServiceImpl.class);
	
	public void run() {
		log.info("2) running the periodicTaskToMoveFromReadyToOngoingqueue thread");
		// from ready to ongoing
		try {
			for (Long gameInstanceId : GameQueueManager.readyGames.keySet()) {

				GameInstance gi = GameQueueManager.readyGames
						.get(gameInstanceId);
				gi.setState(GameConstants.GAME_STATE.ONGOING);

				//gi.setStartTime(System.currentTimeMillis());
				gi.setNoOfPlayersBeaten(gi.getPlayers().size() - 1);

				gi.setGameCreationTime(System.currentTimeMillis());

				log.info("before moving from ready to ongoing game+s");
				QuestionManager.attachQuestionToGameInstance(gi);
				GameQueueManager.ongoingGames.put(gi.getId(), gi);
				GameQueueManager.readyGames.remove(gameInstanceId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
