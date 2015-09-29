package com.core.service.threads.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.Player;
import com.core.manager.CommonQueueManager;
import com.core.manager.ExamSectionGameQueueManager;
import com.core.manager.TopicGameQueueManager;
import com.core.service.threads.InspectAllQueuesService;

@Service("inspectAllQueuesService")
public class InspectAllQueuesServiceImpl 
implements InspectAllQueuesService {

	private static final Logger log = LoggerFactory
			.getLogger(InspectAllQueuesServiceImpl.class);
	public void run() {
		try {
			// new exam section games
			log.info("New exam section games");
			for (Long examSectionId : ExamSectionGameQueueManager.newExamSectionGames.keySet()) {
				log.info(examSectionId + ",");
			}

			// waiting exam section games
			log.info("waiting exam section  games");
			for (Long examSectionId : ExamSectionGameQueueManager.waitingForMorePlayersToJoinExamSectionGames
					.keySet()) {
				log.info(examSectionId + ",");
			}

			// ready exam section games
			log.info("ready exam section games");
			for (Long examSectionId : ExamSectionGameQueueManager.readyExamSectionGames.keySet()) {
				log.info(examSectionId + ",");
			}
			
			// new topic games
			log.info("New topic games");
			for (Long topicId : TopicGameQueueManager.newTopicGames.keySet()) {
				log.info(topicId + ",");
			}

			// waiting topic games
			log.info("waiting topic  games");
			for (Long topicId : TopicGameQueueManager.waitingForMorePlayersToJoinTopicGames
					.keySet()) {
				log.info(topicId + ",");
			}

			// ready games
			log.info("ready topic games");
			for (Long topicId : TopicGameQueueManager.readyTopicGames.keySet()) {
				log.info(topicId + ",");
			}
			// ongoing games
			log.info("Ongoing games");
			for (Long gameInstanceId : CommonQueueManager.ongoingGames
					.keySet()) {
				log.info(gameInstanceId + ",");
			}
			// finished games
			log.info("finished games");
			for (Long gameInstanceId : CommonQueueManager.finishedGames
					.keySet()) {
				log.info(gameInstanceId + ",");
			}
			// expired games
			log.info("Expired games");
			for (Long gameInstanceId : CommonQueueManager.expiredGames
					.keySet()) {
				log.info(gameInstanceId + ",");
			}
			// playerMap queue

			log.info("Player Game map");
			for (Long playerId : CommonQueueManager.playerGameMap.keySet()) {
				log.info(playerId + ",");
				GameInstance gi = CommonQueueManager.playerGameMap
						.get(playerId);
				log.info("num of players in gi with id:" + gi.getId()
						+ " is [" + gi.getNumOfPlayers() + "]");
				log.info("the state of the game is:"
						+ gi.getState().toString());
				for (Player player : gi.getPlayers().values()) {
					if(player != null && player.getUser() != null){
						log.info(" **************the number of lifes for player with id:"
								+ player.getUser().getId()
								+ " is :"
								+ player.getNoOfLife());
					}
				}
			}

			log.info("------------------------------------------");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


}
