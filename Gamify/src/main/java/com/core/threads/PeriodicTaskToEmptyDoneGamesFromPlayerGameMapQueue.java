package com.core.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.domain.knockout.GameInstance;
import com.core.manager.GameQueueManager;

public class PeriodicTaskToEmptyDoneGamesFromPlayerGameMapQueue implements Runnable {

	private static final Logger log = LoggerFactory
			.getLogger(PeriodicTaskToEmptyDoneGamesFromPlayerGameMapQueue.class);
	public void run() {

		// Empty done games. IN real implementation the done games list will
		// be stored in the database and then emptied from memory
		for (Long userId : GameQueueManager.playerGameMap.keySet()) {
			GameInstance gi = GameQueueManager.playerGameMap.get(userId);
			/*
			 * if(GameConstants.GAME_STATE.DONE.equals(gi.getState()) ||
			 * GameConstants.GAME_STATE.EXPIRED.equals(gi.getState())){
			 * 
			 * GameQueueManager.playerGameMap.remove(userId); }
			 */
		}
	}

}
