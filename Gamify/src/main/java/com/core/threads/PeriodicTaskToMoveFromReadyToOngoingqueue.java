package com.core.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.constants.GameConstants;
import com.core.domain.knockout.GameInstance;
import com.core.manager.GameQueueManager;
import com.core.manager.QuestionManager;
import com.core.manager.ThreadManager;

public class PeriodicTaskToMoveFromReadyToOngoingqueue implements Runnable {
	
	private static final Logger log = LoggerFactory
			.getLogger(PeriodicTaskToMoveFromReadyToOngoingqueue.class);
	
	public void run() {
		log.info("2) running the periodicTaskToMoveFromReadyToOngoingqueue thread");
		// from ready to ongoing
		try {
			for (Long gameInstanceId : GameQueueManager.readyGames.keySet()) {

				GameInstance gi = GameQueueManager.readyGames
						.get(gameInstanceId);
				gi.setState(GameConstants.GAME_STATE.ONGOING);
				gi.setStartTime(System.currentTimeMillis());
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
