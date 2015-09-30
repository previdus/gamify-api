package com.core.service.threads.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.constants.GameConstants.GAME_STATE;
import com.core.domain.knockout.GameInstance;
import com.core.manager.CommonQueueManager;
import com.core.manager.ExamSectionGameQueueManager;
import com.core.manager.QuestionManager;
import com.core.manager.TopicGameQueueManager;
import com.core.service.threads.MoveFromReadyToOngoingQueueService;


@Service("moveFromReadyToOngoingQueueService")
public class MoveFromReadyToOngoingQueueServiceImpl implements 
 MoveFromReadyToOngoingQueueService {
	
	private static final Logger log = LoggerFactory
			.getLogger(MoveFromReadyToOngoingQueueServiceImpl.class);
	
	public void run() {
		log.info("2) running the periodicTaskToMoveFromReadyToOngoingqueue thread");
		// from ready to ongoing for exam section
		processReadyGames( ExamSectionGameQueueManager.readyExamSectionGames);
		//from ready to ongoing for topic
		processReadyGames( TopicGameQueueManager.readyTopicGames);
	}

	private void processReadyGames(Map<Long, GameInstance> readyGames) {
		try {
			for (Long key : readyGames.keySet()) {

				GameInstance gi = readyGames
						.get(key);
				gi.setState(GAME_STATE.ONGOING);

				//gi.setStartTime(System.currentTimeMillis());
				gi.setNoOfPlayersBeaten(gi.getPlayers().size() - 1);

				gi.setGameCreationTime(System.currentTimeMillis());

				log.info("before moving from ready to ongoing games");
				QuestionManager.attachQuestionToGameInstance(gi);
				CommonQueueManager.ongoingGames.put(gi.getId(), gi);
				readyGames.remove(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
