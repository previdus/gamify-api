package com.core.manager;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.constants.GameConstants;
import com.core.constants.GameConstants.GAME_DIFFICULTY_LEVEL;
import com.core.constants.GameConstants.GAME_STATE;
import com.core.domain.Option;
import com.core.domain.User;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.PlayerResponseLog;
import com.core.domain.knockout.PreviousQuestionLog;
import com.core.domain.lms.ExamSection;
import com.core.service.AnswerKeyService;
import com.core.service.GameInstanceService;
import com.core.service.UserEloRatingService;
import com.core.service.UserService;

@Component
public class GameQueueManager {

	public static Map<Long, GameInstance> newGames = new ConcurrentHashMap<Long, GameInstance>();
	public static Map<Long, GameInstance> waitingForMorePlayersToJoinGames = new ConcurrentHashMap<Long, GameInstance>();
	public static Map<Long, GameInstance> readyGames = new ConcurrentHashMap<Long, GameInstance>();
	public static Map<Long, GameInstance> ongoingGames = new ConcurrentHashMap<Long, GameInstance>();
	public static Map<Long, GameInstance> finishedGames = new ConcurrentHashMap<Long, GameInstance>();
	public static Map<Long, GameInstance> expriredGames = new ConcurrentHashMap<Long, GameInstance>();
	public static Map<Long, GameInstance> playerGameMap = new ConcurrentHashMap<Long, GameInstance>();
	public static Map<Long, List<PreviousQuestionLog>> gameResponseLog = new ConcurrentHashMap<Long, List<PreviousQuestionLog>>();
	

	private static final Logger log = LoggerFactory
			.getLogger(GameQueueManager.class);

	private static AnswerKeyService answerKeyService;
	
	private static UserService userService;
	
		
		@Autowired(required = true)
		public void setUserService(UserService userService) {
			GameQueueManager.userService = userService;
		}
		
    private static UserEloRatingService userEloRatingService;
	
		
		@Autowired(required = true)
		public void setUserEloRatingService(UserEloRatingService userEloRatingService) {
			GameQueueManager.userEloRatingService = userEloRatingService;
		}

	/**
	 * Sets the answerKeyServiceDao This method should never be called except by
	 * Spring
	 * 
	 * @param userAccessor
	 *            The user accessor to set
	 */
	@Autowired(required = true)
	public void setAnswerKeyService(AnswerKeyService answerKeyService) {
		GameQueueManager.answerKeyService = answerKeyService;
	}
	
	

	private static GameInstanceService gameInstanceService;

	/**
	 * Sets the answerKeyServiceDao This method should never be called except by
	 * Spring
	 * 
	 * @param userAccessor
	 *            The user accessor to set
	 */
	@Autowired(required = true)
	public void setGameInstanceService(GameInstanceService gameInstanceService) {
		GameQueueManager.gameInstanceService = gameInstanceService;
	}

	public static GameInstance getGameInstanceForPlayer(Long playerId) {
		return playerGameMap.get(playerId);
	}
	
	/*
	 * This method is not being called y any other method. Investigate its use. If not delete it
	 */

	public static synchronized GameInstance joinGameInstanceIfUserAlreadyInGame(
			User currentUser) {
		for (Long userId : playerGameMap.keySet()) {

			if (userId.equals(currentUser.getId())) {
				GameInstance gi = playerGameMap.get(userId);
				if (gi.isGameStillActive()) {
					return playerGameMap.get(userId);
				}

			}

		}
		return null;
	}
	
	private static GameInstance joinAnAlreadyExistingGame(GameInstance gi,
			Long examSectionId, 
			User currentUser, 
			Map<Long,GameInstance> queueFromWhereGameNeedsToBeMoved, 
			Map<Long,GameInstance> queueToWhereGameNeedsToBeMoved, 
			GAME_STATE stateToWhichGameNeedsToBeSet,
			boolean setStartWaitTime,
			int numOfPlayersToCheckBeforeChangingState){
		
		if (!gi.isPlayerInGame(currentUser.getId())) {
			gi.addPlayer(currentUser);
			playerGameMap.put(currentUser.getId(), gi);
			if (gi.getNumOfPlayers() == numOfPlayersToCheckBeforeChangingState) {
				if(setStartWaitTime)gi.setStartWaitTime(System.currentTimeMillis());
				gi.setState(stateToWhichGameNeedsToBeSet);
				queueToWhereGameNeedsToBeMoved.put(gi.getId(), gi);
				queueFromWhereGameNeedsToBeMoved.remove(examSectionId);				
				removePlayerFromQuittingQueueIfHeHasTimedOut(gi, currentUser);

			}
		}
		return gi;
	}

	private static void removePlayerFromQuittingQueueIfHeHasTimedOut(
			GameInstance gi, User currentUser) {
		if(gi.getQuittingPlayers() != null && gi.getQuittingPlayers().size() > 0 && gi.getQuittingPlayers().containsKey(currentUser.getId())){
			gi.getQuittingPlayers().remove(currentUser.getId());
		}
	}

	public static synchronized GameInstance createGameInstance(
			ExamSection examSection, User currentUser) {
		GameInstance gi = null;
		if ((gi = waitingForMorePlayersToJoinGames.get(examSection.getId())) != null) {
			
			return joinAnAlreadyExistingGame(gi,examSection.getId(),
					currentUser,
					waitingForMorePlayersToJoinGames,
					readyGames,
					GameConstants.GAME_STATE.READY, 
					false,
					GameConstants.MAXIMUM_NUM_OF_PLAYERS_NEEDED);

		} else if ((gi = newGames.get(examSection.getId())) != null) {
			
			return joinAnAlreadyExistingGame(gi,examSection.getId(),
					currentUser,
					newGames,
					waitingForMorePlayersToJoinGames,
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
		gi.setStartTime(System.currentTimeMillis());
		gi = gameInstanceService.saveOrUpdate(gi);
		newGames.put(examSection.getId(), gi);
		playerGameMap.put(currentUser.getId(), gi);
		return gi;
	}

	public static synchronized boolean checkIfPlayerAlreadyInGame(User user) {
		GameInstance gi = playerGameMap.get(user.getId());
		if (gi != null
				&& GameConstants.GAME_STATE.ONGOING.equals(gi.getState())) {
			return true;
		} else
			return false;
	}

	public static synchronized void removePlayerFromGameIfQuitOrLoggedOutOrSessionExpired(
			User user) {
		if(user != null){
			GameInstance gi = playerGameMap.get(user.getId());
			if (gi != null) {
				gi.removePlayer(user,false);
				playerGameMap.remove(user.getId());
			}
	
			log.info("The userId removed is:" + user.getId());
		}
	}

	public static synchronized GameInstance recordPlayerResponseToQuestion(
			Long userId, Long questionId, Long optionId,
			long secondsTakenToRespond) {
		GameInstance gi = playerGameMap.get(userId);
		if (isTheResponseForCurrentQuestion(questionId, gi)) {
			PlayerResponseLog prl = new PlayerResponseLog(gi.getPlayers().get(
					userId), new User(userId), new Option(optionId),
					secondsTakenToRespond, questionId);
			log.info("just before setting the option id:" + optionId
					+ " for player:" + userId + " for question with id:"
					+ questionId);
			gi.getPlayerResponsesToCurrentQuestion().put(userId, prl);
			if (answerKeyService.isCorrectAnswer(questionId, prl.getResponse())) {
				prl.setResponseCorrect(true);
				if (isThereNoWinnerInThisGameOrIfThisTimeIsTheBest(
						secondsTakenToRespond, gi)) {
					gi.setBestTimeForCurrentQuestion(secondsTakenToRespond);
					gi.setCurrentQuestionWinner(new User(userId));
					prl.setQuestionWinner(true);
				}
			}
			
			if(gi.haveAllPlayersResponded()){
				calculateScoresForPlayers(gi);
			}
		}
		return gi;
	}

	

	private static boolean isThereNoWinnerInThisGameOrIfThisTimeIsTheBest(
			long secondsTakenToRespond, GameInstance gi) {
		return gi.getCurrentQuestionWinner() == null
				|| secondsTakenToRespond < gi
						.getBestTimeForCurrentQuestion();
	}

	private static boolean isTheResponseForCurrentQuestion(Long questionId,
			GameInstance gi) {
		return gi != null && gi.getCurrentQuestion() != null
				&& gi.getCurrentQuestion().getId().equals(questionId);
	}

	public static synchronized void decreaseLife(GameInstance gi, long userId) {
		gi.getPlayers().get(userId)
				.setNoOfLife(gi.getPlayers().get(userId).getNoOfLife() - 1);
		log.info(" Life decreased for userId " + userId + "  Life : "
				+ gi.getPlayers().get(userId).getNoOfLife());
		if (gi.getPlayers().get(userId).getNoOfLife() <= 0) {
			gi.removePlayer(new User(userId),true);
		}
	}

	private static synchronized void manageLife(GameInstance gi) {
		List<Long> playersToBeRemoved = new LinkedList<Long>();
		if (gi.getPlayers() != null) {
			for (long userId : gi.getPlayers().keySet()) {
				if (gi.getCurrentQuestionWinner() == null
						|| (gi.getCurrentQuestionWinner() != null && gi
								.getCurrentQuestionWinner().getId() != userId))
					gi.getPlayers()
							.get(userId)
							.setNoOfLife(
									gi.getPlayers().get(userId).getNoOfLife() - 1);
				log.info(" Life decreased for userId " + userId + "  Life : "
						+ gi.getPlayers().get(userId).getNoOfLife());
				if (gi.getPlayers().get(userId).getNoOfLife() <= 0) {
					playersToBeRemoved.add(userId);
				}
			}
		}
		gi.removePlayers(playersToBeRemoved);
	}

	public static synchronized void calculateScoresForPlayers(GameInstance gi) {

		if (gi.getPlayers() != null
				&& gi.getPlayerResponsesToCurrentQuestion() != null) {

			log.info("debug 6. Number of players are:" + gi.getPlayers().size());
			log.info("debug 7. NUmber of responses are:"
					+ gi.getPlayerResponsesToCurrentQuestion().size());
			if (gi.getPlayers().size() <= gi
					.getPlayerResponsesToCurrentQuestion().size()) {
				userEloRatingService.calulateUserEloRating(gi);
				manageLife(gi);
				QuestionManager.savePreviousQuestionLog(gi);
				if (gi.getPlayers().size() < GameConstants.MINIMUM_NUM_OF_PLAYERS_NEEDED) {
					endGame(gi);					
				}
				else 
				{
					
					loadNextQuestion(gi);
				}

			}
		}
	}

	private static void loadNextQuestion(GameInstance gi) {
		gi.resetCurrentQuestionWinnerAndBestTime();
		QuestionManager.attachQuestionToGameInstance(gi);
		log.info("----- New Question attached is -----------"
				+ gi.getCurrentQuestion().getId().toString());
	}

	public static void endGame(GameInstance gi) {
		log.info("debug Game Done !!!");
		QuestionManager.savePreviousQuestionLog(gi);
		gi.setStateToDone();
		gi.markGameWinner();
		GameQueueManager.finishedGames.put(gi.getId(), gi);
		GameQueueManager.ongoingGames.remove(gi.getId());
		
	}
	
	public static void addBoutUser(long examSectionId) {
				User bautUser = userService.getBautUser();
				if(bautUser == null){
					// do not add - no bout user found
					return;
				}
				createGameInstance(new ExamSection(examSectionId), bautUser);
			}
}
