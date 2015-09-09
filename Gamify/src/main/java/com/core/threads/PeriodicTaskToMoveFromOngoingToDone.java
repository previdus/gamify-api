package com.core.threads;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.domain.knockout.GameInstance;
import com.core.manager.GameQueueManager;

public class PeriodicTaskToMoveFromOngoingToDone implements Runnable {

	private static final Logger log = LoggerFactory
			.getLogger(PeriodicTaskToMoveFromOngoingToDone.class);
	public void run() {

		log.info("4) periodicTaskToMoveFromOngoingToDone");

		// from ongoing to done
		try {
			for (Long gameInstanceId : GameQueueManager.ongoingGames
					.keySet()) {
				GameInstance gi = GameQueueManager.ongoingGames
						.get(gameInstanceId);
				if (hasGameEnded(gi)) {
					GameQueueManager.endGame(gi);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private boolean hasGameEnded(GameInstance gi) {
		
		return gi.getPlayers().size() <= 1 || (gi.getGameWinner() != null);
	}
	


}
