package com.core.service.threads.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.Player;
import com.core.domain.knockout.PlayerResponseLog;
import com.core.manager.GameQueueManager;
import com.core.service.threads.AutomaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTimeService;

@Service("automaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTimeService")
public class AutomaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTimeServiceImpl
		implements 	AutomaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTimeService{

	private static final Logger log = LoggerFactory
			.getLogger(AutomaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTimeServiceImpl.class);
	
	public void run() {

		
        log.info("runnning 9) periodicTaskToAutomaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTime");
		// ongoing games
		for (GameInstance gi : GameQueueManager.ongoingGames.values()) {
			if(gameNotDone(gi)) automaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTime(gi);
		}
	}


	private boolean gameNotDone(GameInstance gi) {
		return !gi.getState().equals(GameConstants.GAME_STATE.DONE);
	}
	

	private  void automaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTime(GameInstance gi){
		
		try{
			Map<Long, PlayerResponseLog> responses =  gi.getPlayerResponsesToCurrentQuestion();
			
			Map<Long, Player> players = gi.getPlayers();
			
			if(isCurrentQuestionExceedingItsTimeLimit(gi)){
				responses = initalizeResponsesIfEmpty(responses);
				log.info("automaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTime - before for loop");
				for(Long userId: players.keySet()){
					if(gi.hasPlayerLostTheGame(userId) && noResponsesReceivedOrPlayerHasntRespondedYet(responses,
							userId)){
						Player player = players.get(
								userId);
						log.info("recording response since its timed out");
						GameQueueManager.recordPlayerResponseToQuestion(player.getUser().getId(),
								gi.getCurrentQuestion().getId(), -1L, 0L);
						
					}
				}
			}
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}


	private Map<Long, PlayerResponseLog> initalizeResponsesIfEmpty(
			Map<Long, PlayerResponseLog> responses) {
		if(responses == null){
			responses = new HashMap<Long, PlayerResponseLog>();
		}
		return responses;
	}


	private boolean noResponsesReceivedOrPlayerHasntRespondedYet(
			Map<Long, PlayerResponseLog> responses, Long playerId) {
		return responses.size() == 0 || !responses.keySet().contains(playerId);
	}
	
	private  boolean isCurrentQuestionExceedingItsTimeLimit(
			GameInstance gi) {
		
		log.info( "(System.currentTimeMillis() - gi.getTimeAtWhichCurrentQuestionWasAttached())/1000 : "+(System.currentTimeMillis() - gi.getTimeAtWhichCurrentQuestionWasAttached())/1000 );
		return (System.currentTimeMillis() - gi.getTimeAtWhichCurrentQuestionWasAttached())/1000 > (gi.getCurrentQuestion().getMaxTimeToAnswerInSeconds() + GameConstants.EXTRA_TIME_NEEDED_TO_WAIT_BEFORE_AUTO_RESPOND_TO_UNANSWERED_QUESTION);
	}

}
