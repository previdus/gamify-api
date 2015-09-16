package com.core.service.threads.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.domain.knockout.GameInstance;
import com.core.manager.GameQueueManager;
import com.core.service.threads.MoveFromWaitingToReadyqueueService;

@Service("moveFromWaitingToReadyqueueService")
public class MoveFromWaitingToReadyqueueServiceImpl implements MoveFromWaitingToReadyqueueService  {

	private static final Logger log = LoggerFactory
			.getLogger(MoveFromWaitingToReadyqueueServiceImpl.class);
	public void run() {

		log.info("1) periodicTaskToMoveFromWaitingToReadyqueue");

		// move from waiting to ready
		try {
			moveNewGamesToWaitingGames();
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
	
	private void moveNewGamesToWaitingGames(){
		try{
			if(GameConstants.ADD_BOUT_USER_AFTER_WAITING_MILLISECONDS <= 0)
				return;
			for (Long examSectionId : GameQueueManager.newGames.keySet()) {
				GameInstance gi = GameQueueManager.newGames.get(examSectionId);
				if(gi != null && gi.getStartTime() == 0){
					gi.setStartTime(System.currentTimeMillis());
				}
				else if(System.currentTimeMillis() - gi.getStartTime() > GameConstants.ADD_BOUT_USER_AFTER_WAITING_MILLISECONDS){
					GameQueueManager.addBoutUser(examSectionId);
				}
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
