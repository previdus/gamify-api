package com.core.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.constants.GameConstants;
import com.core.domain.knockout.GameInstance;
import com.core.manager.GameQueueManager;


public class PeriodicTaskToMoveFromWaitingToReadyqueue implements Runnable {

	private static final Logger log = LoggerFactory
			.getLogger(PeriodicTaskToMoveFromWaitingToReadyqueue.class);
	public void run() {

		log.info("1) periodicTaskToMoveFromWaitingToReadyqueue");

		// move from waiting to ready
		try {
			for (Long examSectionId : GameQueueManager.waitingForMorePlayersToJoinGames
					.keySet()) {

				GameInstance gi = GameQueueManager.waitingForMorePlayersToJoinGames
						.get(examSectionId);
				if (gi.getStartWaitTime() == 0) {
					gi.setStartWaitTime(System.currentTimeMillis());
				} else {
					if ((System.currentTimeMillis() - gi.getStartWaitTime()) / 1000 >= GameConstants.SECONDS_TO_WAIT_FOR_PLAYERS_BEFORE_BEGINNING_GAME) {
						gi.setState(GameConstants.GAME_STATE.READY);
						log.info("before moving to ready games");
						GameQueueManager.readyGames.put(gi.getId(), gi);
						GameQueueManager.waitingForMorePlayersToJoinGames
								.remove(examSectionId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
