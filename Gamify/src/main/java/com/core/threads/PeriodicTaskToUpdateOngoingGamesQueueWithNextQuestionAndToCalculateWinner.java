package com.core.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.domain.knockout.GameInstance;
import com.core.manager.GameQueueManager;

public class PeriodicTaskToUpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinner
		implements Runnable {

	private static final Logger log = LoggerFactory
			.getLogger(PeriodicTaskToUpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinner.class);
	public void run() {
		log.info("3) periodicTaskToUpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinner");
		try {
			for (GameInstance gi : GameQueueManager.ongoingGames.values()) {
				try {
					GameQueueManager.calculateScoresForPlayers(gi);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
