package com.core.service.threads.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.PreviousQuestionLog;
import com.core.manager.CommonQueueManager;
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
			for (Long gameInstanceId : CommonQueueManager.finishedGames
					.keySet()) {
				gameInstance = CommonQueueManager.finishedGames
						.get(gameInstanceId);
				// save in db
				log.info("Prev Question Log Size "
						+ CommonQueueManager.gameResponseLog.get(
								gameInstanceId).size());
				gameInstance
						.setPreviousQuestionLogs(CommonQueueManager.gameResponseLog
								.get(gameInstanceId));
				for(Long key:CommonQueueManager.gameResponseLog.keySet()){
					log.info("for key:"+key);
					List<PreviousQuestionLog> pqlList = CommonQueueManager.gameResponseLog.get(key);
					for(PreviousQuestionLog pql : pqlList){
						if(pql.getQuestion() == null){
							log.info("pql.getQuestion() is null");
						}
						else{
							log.info("pql.getQuestion().getId():"+pql.getQuestion().getId());	
						}
						
					}
					
				}

				log.info("No of Loosers "
						+ gameInstance.getLooserPlayers().size());
				gameInstance.getPlayers().putAll(
						gameInstance.getLooserPlayers());		
			
				gameInstanceService.saveOrUpdate(gameInstance);
				CommonQueueManager.gameResponseLog.remove(gameInstanceId);
			}
			CommonQueueManager.finishedGames.clear();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	

}
