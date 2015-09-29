package com.core.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.core.constants.GameConstants;
import com.core.constants.GameConstants.GAME_DIFFICULTY_LEVEL;
import com.core.domain.User;
import com.core.domain.knockout.GameInstance;
import com.core.domain.lms.ExamSection;

@Component
public class ExamSectionGameQueueManager extends CommonQueueManager
{

	public static Map<Long, GameInstance> newExamSectionGames = new ConcurrentHashMap<Long, GameInstance>();
	public static Map<Long, GameInstance> waitingForMorePlayersToJoinExamSectionGames = new ConcurrentHashMap<Long, GameInstance>();
	public static Map<Long, GameInstance> readyExamSectionGames = new ConcurrentHashMap<Long, GameInstance>();
	
	
	private static final Logger log = LoggerFactory
			.getLogger(ExamSectionGameQueueManager.class);

	
	public static synchronized GameInstance createExamSectionGameInstance(
			ExamSection examSection, User currentUser) {
		GameInstance gi = null;
		if ((gi = waitingForMorePlayersToJoinExamSectionGames.get(examSection.getId())) != null) {
			
			return joinAnAlreadyExistingGame(gi,examSection.getId(),
					currentUser,
					waitingForMorePlayersToJoinExamSectionGames,
					readyExamSectionGames,
					GameConstants.GAME_STATE.READY, 
					false,
					GameConstants.MAXIMUM_NUM_OF_PLAYERS_NEEDED);

		} else if ((gi = newExamSectionGames.get(examSection.getId())) != null) {
			
			return joinAnAlreadyExistingGame(gi,examSection.getId(),
					currentUser,
					newExamSectionGames,
					waitingForMorePlayersToJoinExamSectionGames,
					GameConstants.GAME_STATE.WAITING, 
					true,
					GameConstants.MINIMUM_NUM_OF_PLAYERS_NEEDED);

		}
		else {
			return createNewGame(examSection, currentUser);
		}
	}

	private static GameInstance createNewGame(ExamSection examSection,
			User currentUser) {
		GameInstance gi;
		gi = new GameInstance();
		gi.setDifficultyLevel(GAME_DIFFICULTY_LEVEL.EASY);
		gi.addPlayer(currentUser);
		gi.setExamSection(examSection);
		gi.setState(GameConstants.GAME_STATE.NEW);
		gi.setGameCreationTime(System.currentTimeMillis());
		gi = gameInstanceService.saveOrUpdate(gi);
		newExamSectionGames.put(examSection.getId(), gi);
		playerGameMap.put(currentUser.getId(), gi);
		return gi;
	}
	
	
	
	public static void addBotUser(long keyForTheGameQueue) {
		User botUser = userService.getBotUser();
		if(botUser != null){			
			createExamSectionGameInstance(new ExamSection(keyForTheGameQueue), botUser);
		}
		
	}
}
