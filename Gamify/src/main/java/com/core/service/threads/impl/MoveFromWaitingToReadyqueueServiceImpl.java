package com.core.service.threads.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.domain.knockout.GameInstance;
import com.core.manager.ExamSectionGameQueueManager;
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
			for (Long examSectionId : ExamSectionGameQueueManager.waitingForMorePlayersToJoinExamSectionGames
					.keySet()) {

				GameInstance gi = ExamSectionGameQueueManager.waitingForMorePlayersToJoinExamSectionGames
						.get(examSectionId);
				if (gi.getStartWaitTime() == 0) {
					gi.setStartWaitTime(System.currentTimeMillis());
				} else {
					if ((System.currentTimeMillis() - gi.getStartWaitTime()) / 1000 >= GameConstants.SECONDS_TO_WAIT_FOR_PLAYERS_BEFORE_BEGINNING_GAME) {
						gi.setState(GameConstants.GAME_STATE.READY);
						log.info("before moving to ready games");
						ExamSectionGameQueueManager.readyExamSectionGames.put(gi.getId(), gi);
						ExamSectionGameQueueManager.waitingForMorePlayersToJoinExamSectionGames
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
					if(GameConstants.ADD_BOUT_USER_AFTER_WAITING_MILLISECONDS > 0) {
						for (Long examSectionId : ExamSectionGameQueueManager.newExamSectionGames.keySet()) {
							GameInstance gi = ExamSectionGameQueueManager.newExamSectionGames.get(examSectionId);
							if(gi != null && gi.getGameCreationTime() == 0){
								gi.setGameCreationTime(System.currentTimeMillis());
							}
							else if(System.currentTimeMillis() - gi.getGameCreationTime() > GameConstants.ADD_BOUT_USER_AFTER_WAITING_MILLISECONDS){
								ExamSectionGameQueueManager.addBotUser(examSectionId);
							}
						}
					}
					
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}

}
