package com.core.service.threads.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.core.domain.knockout.GameInstance;
import com.core.manager.ExamSectionGameQueueManager;
import com.core.service.GameInstanceService;
import com.core.service.UserEloRatingService;
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
			for (Long gameInstanceId : ExamSectionGameQueueManager.finishedGames
					.keySet()) {
				gameInstance = ExamSectionGameQueueManager.finishedGames
						.get(gameInstanceId);
				// save in db
				log.info("Prev Question Log Size "
						+ ExamSectionGameQueueManager.gameResponseLog.get(
								gameInstanceId).size());
				gameInstance
						.setPreviousQuestionLogs(ExamSectionGameQueueManager.gameResponseLog
								.get(gameInstanceId));

				log.info("No of Loosers "
						+ gameInstance.getLooserPlayers().size());
				gameInstance.getPlayers().putAll(
						gameInstance.getLooserPlayers());		
				//playerRatingService.calulateRatingAndNumberOfGamesPlayed(gameInstance);
				gameInstanceService.saveOrUpdate(gameInstance);
				ExamSectionGameQueueManager.gameResponseLog.remove(gameInstanceId);
			}
			ExamSectionGameQueueManager.finishedGames.clear();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	

}
