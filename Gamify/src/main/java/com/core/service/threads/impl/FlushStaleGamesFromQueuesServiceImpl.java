package com.core.service.threads.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.Player;
import com.core.manager.GameQueueManager;
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
			for (Long examSectionId : GameQueueManager.newGames.keySet()) {
				GameInstance gi = GameQueueManager.newGames
						.get(examSectionId);
				if (gi.getPlayers() == null || gi.getPlayers().size() == 0) {
					gi.setState(GameConstants.GAME_STATE.EXPIRED);
					GameQueueManager.newGames.remove(examSectionId);
					GameQueueManager.expriredGames.put(gi.getId(), gi);
				}
			}

			// for waiting games
			for (Long examSectionId : GameQueueManager.waitingForMorePlayersToJoinGames
					.keySet()) {
				GameInstance gi = GameQueueManager.waitingForMorePlayersToJoinGames
						.get(examSectionId);
				if (gi.getPlayers() != null) {
					if (gi.getPlayers().size() == 1) {
						Player player = gi.getPlayers().get(0);
						GameQueueManager.waitingForMorePlayersToJoinGames
								.remove(examSectionId);
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						GameQueueManager.expriredGames.put(gi.getId(), gi);
						GameInstance gameAlreadyReadyForThisPlayer = GameQueueManager.readyGames
								.get(gi.getId());
						if (gameAlreadyReadyForThisPlayer == null) {
							GameQueueManager.createGameInstance(
									gi.getExamSection(), player.getUser());
						} else {
							GameQueueManager.playerGameMap.put(player
									.getUser().getId(),
									gameAlreadyReadyForThisPlayer);
							gameAlreadyReadyForThisPlayer
									.addPlayerFromAnotherGame(player);
						}

					} else if (gi.getPlayers().size() == 0) {
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						GameQueueManager.expriredGames.put(gi.getId(), gi);
						GameQueueManager.waitingForMorePlayersToJoinGames
								.remove(examSectionId);
					}

				} else {
					gi.setState(GameConstants.GAME_STATE.EXPIRED);
					GameQueueManager.expriredGames.put(gi.getId(), gi);
					GameQueueManager.waitingForMorePlayersToJoinGames
							.remove(examSectionId);
				}

			}

			// for ready games
			for (Long gameInstanceId : GameQueueManager.readyGames.keySet()) {
				GameInstance gi = GameQueueManager.readyGames
						.get(gameInstanceId);
				if (gi.getPlayers() != null) {
					if (gi.getPlayers().size() == 0) {
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						GameQueueManager.expriredGames.put(gi.getId(), gi);
						GameQueueManager.readyGames.remove(gameInstanceId);
					} else if (gi.getPlayers().size() < 2) {
						gi.setState(GameConstants.GAME_STATE.WAITING);
						GameQueueManager.waitingForMorePlayersToJoinGames
								.put(gi.getId(), gi);
						GameQueueManager.readyGames.remove(gameInstanceId);
					}
				} else {
					gi.setState(GameConstants.GAME_STATE.EXPIRED);
					GameQueueManager.expriredGames.put(gi.getId(), gi);
					GameQueueManager.readyGames.remove(gameInstanceId);
				}
			}

			// for ongoing games
			for (Long gameInstanceId : GameQueueManager.ongoingGames
					.keySet()) {
				GameInstance gi = GameQueueManager.ongoingGames
						.get(gameInstanceId);
				if (gi.getPlayers() != null) {
					if (gi.getPlayers().size() == 1) {
						Player player = gi.getPlayers().get(0);
						GameQueueManager.ongoingGames
								.remove(gameInstanceId);
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						GameQueueManager.expriredGames.put(gameInstanceId,
								gi);
						GameInstance gameAlreadyReadyForThisPlayer = GameQueueManager.readyGames
								.get(gi.getId());
						if (gameAlreadyReadyForThisPlayer == null) {
							GameQueueManager.createGameInstance(
									gi.getExamSection(), player.getUser());
						} else {
							GameQueueManager.playerGameMap.put(player
									.getUser().getId(),
									gameAlreadyReadyForThisPlayer);
							gameAlreadyReadyForThisPlayer
									.addPlayerFromAnotherGame(player);
						}

					} else if (gi.getPlayers().size() == 0) {
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						GameQueueManager.expriredGames.put(gameInstanceId,
								gi);
						GameQueueManager.ongoingGames
								.remove(gameInstanceId);

					}

				} else {
					gi.setState(GameConstants.GAME_STATE.EXPIRED);
					GameQueueManager.expriredGames.put(gi.getId(), gi);
					GameQueueManager.ongoingGames.remove(gameInstanceId);
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


}
