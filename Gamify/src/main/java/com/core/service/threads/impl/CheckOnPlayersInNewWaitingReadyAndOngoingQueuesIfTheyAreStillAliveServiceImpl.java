package com.core.service.threads.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.Player;
import com.core.manager.CommonQueueManager;
import com.core.manager.ExamSectionGameQueueManager;
import com.core.manager.TopicGameQueueManager;
import com.core.service.threads.CheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAliveService;

@Service("checkOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAliveService")
public class CheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAliveServiceImpl
		implements CheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAliveService {

	private static final Logger log = LoggerFactory
			.getLogger(CheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAliveServiceImpl.class);
	public void run() {

		// new exam section games
		for (GameInstance gi : ExamSectionGameQueueManager.newExamSectionGames.values()) {
			removePlayerFromGameIfHisPollsAreMissedForALongTime(gi);
		}

		// waiting exam section games
		for (GameInstance gi : ExamSectionGameQueueManager.waitingForMorePlayersToJoinExamSectionGames
				.values()) {
			removePlayerFromGameIfHisPollsAreMissedForALongTime(gi);
		}

		// ready exam section games
		for (GameInstance gi : ExamSectionGameQueueManager.readyExamSectionGames.values()) {
			removePlayerFromGameIfHisPollsAreMissedForALongTime(gi);
		}
		
		// new topic games
		for (GameInstance gi : TopicGameQueueManager.newTopicGames.values()) {
			removePlayerFromGameIfHisPollsAreMissedForALongTime(gi);
		}

		// waiting topic games
		for (GameInstance gi : TopicGameQueueManager.waitingForMorePlayersToJoinTopicGames
				.values()) {
			removePlayerFromGameIfHisPollsAreMissedForALongTime(gi);
		}

		// ready topic games
		for (GameInstance gi : TopicGameQueueManager.readyTopicGames.values()) {
			removePlayerFromGameIfHisPollsAreMissedForALongTime(gi);
		}

		// ongoing games
		for (GameInstance gi : CommonQueueManager.ongoingGames.values()) {
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
						.currentTimeMillis() - player.getPlayerJoinTime()) / (Long)GameConstants.CONFIGURATION_MAP.get(GameConstants.PLAYER_IN_GAME_POLL_TIME_KEY);
				player.setNumOfExpectedPollsSincePlayerJoined(numOfExpectedPollsSincePlayerJoined);
				log.info("numOfExpectedPollsSincePlayerJoined is :"+numOfExpectedPollsSincePlayerJoined);
				if (getNumberOfMissedPolls(player, numOfExpectedPollsSincePlayerJoined) > (Integer)GameConstants.CONFIGURATION_MAP.get(GameConstants.MINIMUM_NUMBER_OF_POLLS_MISSED_BY_PLAYER_BEFORE_DECIDING_TO_REMOVE_PLAYER_FROM_GAME_KEY)) {
					gi.removePlayer(player.getUser(),false);
					CommonQueueManager.playerGameMap.remove(player.getUser().getId());
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
