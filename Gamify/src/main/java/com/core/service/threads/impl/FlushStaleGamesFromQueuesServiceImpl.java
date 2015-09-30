package com.core.service.threads.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.domain.knockout.GameInstance;
import com.core.manager.CommonQueueManager;
import com.core.manager.ExamSectionGameQueueManager;
import com.core.manager.TopicGameQueueManager;
import com.core.service.threads.FlushStaleGamesFromQueuesService;

@Service("flushStaleGamesFromQueuesService")
public class FlushStaleGamesFromQueuesServiceImpl 
implements FlushStaleGamesFromQueuesService {

	private static final Logger log = LoggerFactory
			.getLogger(FlushStaleGamesFromQueuesServiceImpl.class);
	public void run() {
		try {
			log.info("5) periodicTaskToFlushStaleGamesFromQueues");

			// flush playerGameMap

			// for(Long playerId: GameQueueManager.playerGameMap.keySet()){
			// GameInstance gi =
			// GameQueueManager.playerGameMap.get(playerId);
			// if(gi.getState().equals(GameConstants.GAME_STATE.DONE)){
			// GameQueueManager.playerGameMap.remove(playerId);
			// }
			// }

			/*
			 * Make sure games are moved to expired state if they become
			 * stale
			 */

			// for new exam section games
			expireGameQueue(ExamSectionGameQueueManager.newExamSectionGames);
			//for new topic games
			expireGameQueue(TopicGameQueueManager.newTopicGames);

			//for waiting exam section games
			expireGameQueue(ExamSectionGameQueueManager.waitingForMorePlayersToJoinExamSectionGames);
			
			//for waiting topic games
			expireGameQueue(TopicGameQueueManager.waitingForMorePlayersToJoinTopicGames);

			//for ready exam section games
			expireReadyGameQueue( ExamSectionGameQueueManager.readyExamSectionGames,ExamSectionGameQueueManager.waitingForMorePlayersToJoinExamSectionGames);
			
			//for ready topic games
			expireReadyGameQueue( TopicGameQueueManager.readyTopicGames,TopicGameQueueManager.waitingForMorePlayersToJoinTopicGames);

			// for ongoing games
			expireOngoingGames();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	private void expireOngoingGames() {
		for (Long gameInstanceId : CommonQueueManager.ongoingGames
				.keySet()) {
			GameInstance gi = CommonQueueManager.ongoingGames
					.get(gameInstanceId);
			if (gi.getPlayers() == null || gi.getPlayerResponsesToCurrentQuestion().size() == 0) {
				expireGame(CommonQueueManager.ongoingGames,gameInstanceId,gi);

			} 

		}
	}


	private void expireReadyGameQueue(Map<Long, GameInstance> readyGameQueue, Map<Long, GameInstance> waitingGameQueue) {
		// for ready games
		for (Long key : readyGameQueue.keySet()) {
			GameInstance gi = readyGameQueue
					.get(key);
			if (gi.getPlayers() != null) {
				if (gi.getPlayers().size() == 0) {
					expireGame(readyGameQueue,key,gi);
				} else if (gi.getPlayers().size() < 2) {
					gi.setState(GameConstants.GAME_STATE.WAITING);
					waitingGameQueue
							.put(key, gi);
					readyGameQueue.remove(key);
				}
			} else {
				
				expireGame(ExamSectionGameQueueManager.readyExamSectionGames,key,gi);
			}
		}
	}
	
	
	private void expireGameQueue(Map<Long,GameInstance> queueToBeProcessed) {
		for (Long key : queueToBeProcessed.keySet()) {
			GameInstance gi = queueToBeProcessed
					.get(key);
			if (gi.getPlayers() == null || gi.getPlayers().size() == 0) {
				
				expireGame(queueToBeProcessed, key, gi);
			}
		}
	}
	private void expireGame(Map<Long, GameInstance> queueToBeProcessed,
			Long key, GameInstance gi) {
		log.info("expiring game-"+gi.getId());
		gi.setState(GameConstants.GAME_STATE.EXPIRED);
		queueToBeProcessed.remove(key);
		CommonQueueManager.expiredGames.put(gi.getId(), gi);
	}
	
	


}
