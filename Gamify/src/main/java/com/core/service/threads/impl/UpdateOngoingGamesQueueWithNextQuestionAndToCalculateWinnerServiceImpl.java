package com.core.service.threads.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.core.domain.knockout.GameInstance;
import com.core.manager.ExamSectionGameQueueManager;
import com.core.service.threads.UpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinnerService;

@Service("updateOngoingGamesQueueWithNextQuestionAndToCalculateWinnerService")
public class UpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinnerServiceImpl
		implements UpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinnerService {

	private static final Logger log = LoggerFactory
			.getLogger(UpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinnerServiceImpl.class);
	public void run() {
		log.info("3) periodicTaskToUpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinner");
		try {
			for (GameInstance gi : ExamSectionGameQueueManager.ongoingGames.values()) {
				try {
					ExamSectionGameQueueManager.calculateScoresForPlayers(gi);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
