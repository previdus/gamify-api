package com.core.service.threads.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.Player;
import com.core.manager.GameQueueManager;
import com.core.service.threads.InspectAllQueuesService;

@Service("inspectAllQueuesService")
public class InspectAllQueuesServiceImpl 
implements InspectAllQueuesService {

	private static final Logger log = LoggerFactory
			.getLogger(InspectAllQueuesServiceImpl.class);
	public void run() {
		try {
			// new games
			log.info("New games");
			for (Long examSectionId : GameQueueManager.newGames.keySet()) {
				log.info(examSectionId + ",");
			}

			// waiting games
			log.info("waiting games");
			for (Long examSectionId : GameQueueManager.waitingForMorePlayersToJoinGames
					.keySet()) {
				log.info(examSectionId + ",");
			}

			// ready games
			log.info("ready games");
			for (Long examSectionId : GameQueueManager.readyGames.keySet()) {
				log.info(examSectionId + ",");
			}
			// ongoing games
			log.info("Ongoing games");
			for (Long gameInstanceId : GameQueueManager.ongoingGames
					.keySet()) {
				log.info(gameInstanceId + ",");
			}
			// finished games
			log.info("finished games");
			for (Long gameInstanceId : GameQueueManager.finishedGames
					.keySet()) {
				log.info(gameInstanceId + ",");
			}
			// expired games
			log.info("Expired games");
			for (Long gameInstanceId : GameQueueManager.expriredGames
					.keySet()) {
				log.info(gameInstanceId + ",");
			}
			// playerMap queue

			log.info("Player Game map");
			for (Long playerId : GameQueueManager.playerGameMap.keySet()) {
				log.info(playerId + ",");
				GameInstance gi = GameQueueManager.playerGameMap
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
