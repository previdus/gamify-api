package com.core.service.threads.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.Player;
import com.core.manager.GameQueueManager;
import com.core.service.threads.CheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAliveService;

@Service("checkOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAliveService")
public class CheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAliveServiceImpl
		implements CheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAliveService {

	private static final Logger log = LoggerFactory
			.getLogger(CheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAliveServiceImpl.class);
	public void run() {

		// new games
		for (GameInstance gi : GameQueueManager.newGames.values()) {
			removePlayerFromGameIfHisPollsAreMissedForALongTime(gi);
		}

		// waiting games
		for (GameInstance gi : GameQueueManager.waitingForMorePlayersToJoinGames
				.values()) {
			removePlayerFromGameIfHisPollsAreMissedForALongTime(gi);
		}

		// ready games
		for (GameInstance gi : GameQueueManager.readyGames.values()) {
			removePlayerFromGameIfHisPollsAreMissedForALongTime(gi);
		}

		// ongoing games
		for (GameInstance gi : GameQueueManager.ongoingGames.values()) {
			removePlayerFromGameIfHisPollsAreMissedForALongTime(gi);
		}
	}
	
	private void removePlayerFromGameIfHisPollsAreMissedForALongTime(
			GameInstance gi) {
		log.info("Running thread 8) removePlayerFromGameIfHisPollsAreMissedForALongTime");
		try{
			for (Player player : gi.getPlayers().values()) {
				log.info("player currently in the game is:"+player.getUser().getDisplayName());
				long numOfExpectedPollsSincePlayerJoined = (System
						.currentTimeMillis() - player.getPlayerJoinTime()) / GameConstants.PLAYER_IN_GAME_POLL_TIME;
				player.setNumOfExpectedPollsSincePlayerJoined(numOfExpectedPollsSincePlayerJoined);
				log.info("numOfExpectedPollsSincePlayerJoined is :"+numOfExpectedPollsSincePlayerJoined);
				if (getNumberOfMissedPolls(player, numOfExpectedPollsSincePlayerJoined) > GameConstants.MINIMUM_NUMBER_OF_POLLS_MISSED_BY_PLAYER_BEFORE_DECIDING_TO_REMOVE_PLAYER_FROM_GAME) {
					gi.removePlayer(player.getUser(),gi.hasPlayerLostTheGame(player.getUser().getId()));
					GameQueueManager.playerGameMap.remove(player.getUser().getId());
				}
			}
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	
	private  long getNumberOfMissedPolls(Player player,
			long numOfExpectedPollsSincePlayerJoined) {
		long numOfMissedPolls = numOfExpectedPollsSincePlayerJoined - player
				.getNoOfPollsSoFar();
		log.info("numOfMissedPolls:"+numOfMissedPolls);
		return numOfMissedPolls;
		
	}

}
