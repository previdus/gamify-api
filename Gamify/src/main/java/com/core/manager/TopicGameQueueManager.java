package com.core.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.core.constants.GameConstants;
import com.core.constants.GameConstants.GAME_STATE;
import com.core.domain.User;
import com.core.domain.knockout.GameInstance;
import com.core.domain.lms.Topic;

@Component
public class TopicGameQueueManager extends CommonQueueManager
{

	public static Map<Long, GameInstance> newTopicGames = new ConcurrentHashMap<Long, GameInstance>();
	public static Map<Long, GameInstance> waitingForMorePlayersToJoinTopicGames = new ConcurrentHashMap<Long, GameInstance>();
	public static Map<Long, GameInstance> readyTopicGames = new ConcurrentHashMap<Long, GameInstance>();
	
	
	private static final Logger log = LoggerFactory
			.getLogger(TopicGameQueueManager.class);

	
	public static synchronized GameInstance createTopicGameInstance(
			Topic topic, User currentUser) {
		GameInstance gi = null;
		if ((gi = waitingForMorePlayersToJoinTopicGames.get(topic.getId())) != null) {
			
			return joinAnAlreadyExistingGame(gi,topic.getId(),
					currentUser,
					waitingForMorePlayersToJoinTopicGames,
					readyTopicGames,
					GAME_STATE.READY, 
					false,
					(Short)GameConstants.CONFIGURATION_MAP.get(GameConstants.MAXIMUM_NUM_OF_PLAYERS_NEEDED_KEY));

		} else if ((gi = newTopicGames.get(topic.getId())) != null) {
			
			return joinAnAlreadyExistingGame(gi,topic.getId(),
					currentUser,
					newTopicGames,
					waitingForMorePlayersToJoinTopicGames,
					GAME_STATE.WAITING, 
					true,
					(Short)GameConstants.CONFIGURATION_MAP.get(GameConstants.MINIMUM_NUM_OF_PLAYERS_NEEDED_KEY));

		}
		else {
			return createNewGame(topic.fetchExamSection(),topic, topic.getId(), currentUser,newTopicGames);
		}
	}

	
	
	
	public static void addBotUser(long keyForTheGameQueue) {
		User botUser = userService.getBotUser();
		if(botUser != null){			
			createTopicGameInstance(new Topic(keyForTheGameQueue), botUser);
		}
		
	}
}
