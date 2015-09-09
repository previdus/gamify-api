package com.core.service.threads.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.core.domain.knockout.GameInstance;
import com.core.manager.GameQueueManager;
import com.core.service.GameInstanceService;
import com.core.service.threads.StoreFinishedGamesAndEmptyQueueService;

@Service("storeFinishedGamesAndEmptyQueueService")
public class StoreFinishedGamesAndEmptyQueueServiceImpl 
implements StoreFinishedGamesAndEmptyQueueService {

	private static final Logger log = LoggerFactory
			.getLogger(StoreFinishedGamesAndEmptyQueueServiceImpl.class);
	
	private  GameInstanceService gameInstanceService;

	@Autowired(required = true)
	public void setGameInstanceService(GameInstanceService gameInstanceService) {
		this.gameInstanceService = gameInstanceService;
	}
	
	public void run() {
		log.info("*********************storeFinishedGamesAndEmptyQueue***********************");
		try {
			GameInstance gameInstance = null;
			for (Long gameInstanceId : GameQueueManager.finishedGames
					.keySet()) {
				gameInstance = GameQueueManager.finishedGames
						.get(gameInstanceId);
				// save in db
				log.info("Prev Question Log Size "
						+ GameQueueManager.gameResponseLog.get(
								gameInstanceId).size());
				gameInstance
						.setPreviousQuestionLogs(GameQueueManager.gameResponseLog
								.get(gameInstanceId));

				log.info("No of Loosers "
						+ gameInstance.getLooserPlayers().size());
				gameInstance.getPlayers().putAll(
						gameInstance.getLooserPlayers());				
				gameInstanceService.saveOrUpdate(gameInstance);
				GameQueueManager.gameResponseLog.remove(gameInstanceId);
			}
			GameQueueManager.finishedGames.clear();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
