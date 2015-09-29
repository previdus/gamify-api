package com.core.service.threads.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.Player;
import com.core.manager.ExamSectionGameQueueManager;
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

			// for new games
			for (Long examSectionId : ExamSectionGameQueueManager.newExamSectionGames.keySet()) {
				GameInstance gi = ExamSectionGameQueueManager.newExamSectionGames
						.get(examSectionId);
				if (gi.getPlayers() == null || gi.getPlayers().size() == 0) {
					gi.setState(GameConstants.GAME_STATE.EXPIRED);
					ExamSectionGameQueueManager.newExamSectionGames.remove(examSectionId);
					ExamSectionGameQueueManager.expiredGames.put(gi.getId(), gi);
				}
			}

			// for waiting games
			for (Long examSectionId : ExamSectionGameQueueManager.waitingForMorePlayersToJoinExamSectionGames
					.keySet()) {
				GameInstance gi = ExamSectionGameQueueManager.waitingForMorePlayersToJoinExamSectionGames
						.get(examSectionId);
				if (gi.getPlayers() != null) {
					if (gi.getPlayers().size() == 1) {
						Player player = gi.getPlayers().get(0);
						ExamSectionGameQueueManager.waitingForMorePlayersToJoinExamSectionGames
								.remove(examSectionId);
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						ExamSectionGameQueueManager.expiredGames.put(gi.getId(), gi);
						GameInstance gameAlreadyReadyForThisPlayer = ExamSectionGameQueueManager.readyExamSectionGames
								.get(gi.getId());
						if (gameAlreadyReadyForThisPlayer == null) {
							ExamSectionGameQueueManager.createExamSectionGameInstance(
									gi.getExamSection(), player.getUser());
						} else {
							ExamSectionGameQueueManager.playerGameMap.put(player
									.getUser().getId(),
									gameAlreadyReadyForThisPlayer);
							gameAlreadyReadyForThisPlayer
									.addPlayerFromAnotherGame(player);
						}

					} else if (gi.getPlayers().size() == 0) {
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						ExamSectionGameQueueManager.expiredGames.put(gi.getId(), gi);
						ExamSectionGameQueueManager.waitingForMorePlayersToJoinExamSectionGames
								.remove(examSectionId);
					}

				} else {
					gi.setState(GameConstants.GAME_STATE.EXPIRED);
					ExamSectionGameQueueManager.expiredGames.put(gi.getId(), gi);
					ExamSectionGameQueueManager.waitingForMorePlayersToJoinExamSectionGames
							.remove(examSectionId);
				}

			}

			// for ready games
			for (Long gameInstanceId : ExamSectionGameQueueManager.readyExamSectionGames.keySet()) {
				GameInstance gi = ExamSectionGameQueueManager.readyExamSectionGames
						.get(gameInstanceId);
				if (gi.getPlayers() != null) {
					if (gi.getPlayers().size() == 0) {
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						ExamSectionGameQueueManager.expiredGames.put(gi.getId(), gi);
						ExamSectionGameQueueManager.readyExamSectionGames.remove(gameInstanceId);
					} else if (gi.getPlayers().size() < 2) {
						gi.setState(GameConstants.GAME_STATE.WAITING);
						ExamSectionGameQueueManager.waitingForMorePlayersToJoinExamSectionGames
								.put(gi.getId(), gi);
						ExamSectionGameQueueManager.readyExamSectionGames.remove(gameInstanceId);
					}
				} else {
					gi.setState(GameConstants.GAME_STATE.EXPIRED);
					ExamSectionGameQueueManager.expiredGames.put(gi.getId(), gi);
					ExamSectionGameQueueManager.readyExamSectionGames.remove(gameInstanceId);
				}
			}

			// for ongoing games
			for (Long gameInstanceId : ExamSectionGameQueueManager.ongoingGames
					.keySet()) {
				GameInstance gi = ExamSectionGameQueueManager.ongoingGames
						.get(gameInstanceId);
				if (gi.getPlayers() != null) {
					if (gi.getPlayers().size() == 1) {
						Player player = gi.getPlayers().get(0);
						ExamSectionGameQueueManager.ongoingGames
								.remove(gameInstanceId);
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						ExamSectionGameQueueManager.expiredGames.put(gameInstanceId,
								gi);
						GameInstance gameAlreadyReadyForThisPlayer = ExamSectionGameQueueManager.readyExamSectionGames
								.get(gi.getId());
						if (gameAlreadyReadyForThisPlayer == null) {
							ExamSectionGameQueueManager.createExamSectionGameInstance(
									gi.getExamSection(), player.getUser());
						} else {
							ExamSectionGameQueueManager.playerGameMap.put(player
									.getUser().getId(),
									gameAlreadyReadyForThisPlayer);
							gameAlreadyReadyForThisPlayer
									.addPlayerFromAnotherGame(player);
						}

					} else if (gi.getPlayers().size() == 0) {
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						ExamSectionGameQueueManager.expiredGames.put(gameInstanceId,
								gi);
						ExamSectionGameQueueManager.ongoingGames
								.remove(gameInstanceId);

					}

				} else {
					gi.setState(GameConstants.GAME_STATE.EXPIRED);
					ExamSectionGameQueueManager.expiredGames.put(gi.getId(), gi);
					ExamSectionGameQueueManager.ongoingGames.remove(gameInstanceId);
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


}
