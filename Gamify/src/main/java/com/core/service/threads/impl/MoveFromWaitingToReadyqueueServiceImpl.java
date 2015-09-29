package com.core.service.threads.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.domain.knockout.GameInstance;
import com.core.manager.ExamSectionGameQueueManager;
import com.core.manager.TopicGameQueueManager;
import com.core.service.threads.MoveFromWaitingToReadyqueueService;

@Service("moveFromWaitingToReadyqueueService")
public class MoveFromWaitingToReadyqueueServiceImpl implements MoveFromWaitingToReadyqueueService  {

	private static final Logger log = LoggerFactory
			.getLogger(MoveFromWaitingToReadyqueueServiceImpl.class);
	public void run() {

		log.info("1) periodicTaskToMoveFromWaitingToReadyqueue");

		// move from waiting to ready for exam section games
		processMoveGamesFromWaitingToReady(ExamSectionGameQueueManager.waitingForMorePlayersToJoinExamSectionGames, 
				ExamSectionGameQueueManager.readyExamSectionGames, ExamSectionGameQueueManager.newExamSectionGames,true);
		
		//move from waiting to ready for topic games
		processMoveGamesFromWaitingToReady(TopicGameQueueManager.waitingForMorePlayersToJoinTopicGames, 
				TopicGameQueueManager.readyTopicGames, TopicGameQueueManager.newTopicGames,false);
	}

	private void processMoveGamesFromWaitingToReady(Map<Long, GameInstance> waitingGameQueue, Map<Long, GameInstance> readyGameQueue, Map<Long, GameInstance> newGameQueue, boolean atWhatLevelToAddBot) {
		try {
			moveNewGamesToWaitingGames(newGameQueue, atWhatLevelToAddBot);
			for (Long key : waitingGameQueue
					.keySet()) {

				GameInstance gi = waitingGameQueue
						.get(key);
				if (gi.getStartWaitTime() == 0) {
					gi.setStartWaitTime(System.currentTimeMillis());
				} else {
					if ((System.currentTimeMillis() - gi.getStartWaitTime()) / 1000 >= GameConstants.SECONDS_TO_WAIT_FOR_PLAYERS_BEFORE_BEGINNING_GAME) {
						gi.setState(GameConstants.GAME_STATE.READY);
						log.info("before moving to ready games");
						readyGameQueue.put(key, gi);
						waitingGameQueue
								.remove(key);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void moveNewGamesToWaitingGames(Map<Long, GameInstance> newGameQueue, boolean examSectionLevel){
		
		if(GameConstants.ADD_BOT_USER_AFTER_WAITING_MILLISECONDS > 0) {
			for (Long key : newGameQueue.keySet()) {
				GameInstance gi = newGameQueue.get(key);
				if(gi != null && gi.getGameCreationTime() == 0){
					gi.setGameCreationTime(System.currentTimeMillis());
				}
				else if(System.currentTimeMillis() - gi.getGameCreationTime() > GameConstants.ADD_BOT_USER_AFTER_WAITING_MILLISECONDS){
					if(examSectionLevel){
						ExamSectionGameQueueManager.addBotUser(key);
					}
					else{
						TopicGameQueueManager.addBotUser(key);
					}
				}
			}
		}
		
		
	}

}
