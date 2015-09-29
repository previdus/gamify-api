package com.core.service.threads.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.core.domain.knockout.GameInstance;
import com.core.manager.ExamSectionGameQueueManager;
import com.core.service.threads.EmptyDoneGamesFromPlayerGameMapQueueService;

@Service("emptyDoneGamesFromPlayerGameMapQueueService")
public class EmptyDoneGamesFromPlayerGameMapQueueServiceImpl 
implements Runnable,EmptyDoneGamesFromPlayerGameMapQueueService {

	private static final Logger log = LoggerFactory
			.getLogger(EmptyDoneGamesFromPlayerGameMapQueueServiceImpl.class);
	public void run() {

		// Empty done games. IN real implementation the done games list will
		// be stored in the database and then emptied from memory
		for (Long userId : ExamSectionGameQueueManager.playerGameMap.keySet()) {
			GameInstance gi = ExamSectionGameQueueManager.playerGameMap.get(userId);
			/*
			 * if(GameConstants.GAME_STATE.DONE.equals(gi.getState()) ||
			 * GameConstants.GAME_STATE.EXPIRED.equals(gi.getState())){
			 * 
			 * GameQueueManager.playerGameMap.remove(userId); }
			 */
		}
	}

}
