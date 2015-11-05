package com.core.manager;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.core.constants.GameConstants;
import com.core.constants.GameConstants.GAME_DIFFICULTY_LEVEL;
import com.core.constants.GameConstants.GAME_STATE;
import com.core.domain.Option;
import com.core.domain.User;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.PlayerResponseLog;
import com.core.domain.knockout.PreviousQuestionLog;
import com.core.domain.lms.ExamSection;
import com.core.domain.lms.Topic;
import com.core.service.AnswerKeyService;
import com.core.service.GameInstanceService;
import com.core.service.UserEloRatingService;
import com.core.service.UserPointsService;
import com.core.service.UserService;

public class CommonQueueManager {
	
	public static Map<Long, GameInstance> playerGameMap = new ConcurrentHashMap<Long, GameInstance>();
	public static Map<Long, GameInstance> ongoingGames = new ConcurrentHashMap<Long, GameInstance>();
	public static Map<Long, GameInstance> finishedGames = new ConcurrentHashMap<Long, GameInstance>();
	public static Map<Long, GameInstance> expiredGames = new ConcurrentHashMap<Long, GameInstance>();
    
	
	
	public static Map<Long, List<PreviousQuestionLog>> gameResponseLog = new ConcurrentHashMap<Long, List<PreviousQuestionLog>>();
	
	private static final Logger log = LoggerFactory
			.getLogger(CommonQueueManager.class);

	
    protected static AnswerKeyService answerKeyService;
	
	protected static UserService userService;
		
	@Autowired(required = true)
	public void setUserService(UserService userService) {
		CommonQueueManager.userService = userService;
	}
		
    protected static UserEloRatingService userEloRatingService;
	
		
	@Autowired(required = true)
	public void setUserEloRatingService(UserEloRatingService userEloRatingService) {
		CommonQueueManager.userEloRatingService = userEloRatingService;
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
		CommonQueueManager.answerKeyService = answerKeyService;
	}
	
	protected static GameInstanceService gameInstanceService;

	protected static UserPointsService userPointsService;
	
	@Autowired(required = true)
	public void setUserPointsService(UserPointsService userPointsService) {
		CommonQueueManager.userPointsService = userPointsService;
	}

	/**
	 * Sets the answerKeyServiceDao This method should never be called except by
	 * Spring
	 * 
	 * @param userAccessor
	 *            The user accessor to set
	 */
	@Autowired(required = true)
	public void setGameInstanceService(GameInstanceService gameInstanceService) {
		CommonQueueManager.gameInstanceService = gameInstanceService;
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
	
	protected static GameInstance joinAnAlreadyExistingGame(GameInstance gi,
			Long keyForTheQueues, 
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
				queueFromWhereGameNeedsToBeMoved.remove(keyForTheQueues);				
				removePlayerFromQuittingQueueIfHeHasTimedOut(gi, currentUser);

			}
		}
		return gi;
	}
	
	protected static GameInstance createNewGame(ExamSection es, Topic topic, Long keyForNewGameQueue,
			User currentUser, Map<Long,GameInstance> newGameQueue) {
		GameInstance gi;
		gi = new GameInstance();
		gi.setDifficultyLevel(GAME_DIFFICULTY_LEVEL.EASY);
		gi.addPlayer(currentUser);
		gi.setExamSection(es);
		gi.setTopic(topic);
		gi.setState(GAME_STATE.NEW);
		gi.setGameCreationTime(System.currentTimeMillis());
		gi = gameInstanceService.saveOrUpdate(gi);
		newGameQueue.put(keyForNewGameQueue, gi);
		playerGameMap.put(currentUser.getId(), gi);
		return gi;
	}
	

	private static void removePlayerFromQuittingQueueIfHeHasTimedOut(
			GameInstance gi, User currentUser) {
		if(gi.getQuittingPlayers() != null && gi.getQuittingPlayers().size() > 0 && gi.getQuittingPlayers().containsKey(currentUser.getId())){
			gi.getQuittingPlayers().remove(currentUser.getId());
		}
	}
	
	public static synchronized boolean checkIfPlayerAlreadyInGame(User user) {
		GameInstance gi = playerGameMap.get(user.getId());
		if (gi != null
				&& GAME_STATE.ONGOING.equals(gi.getState())) {
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
				
				if (gi.getPlayers().size() < (Short)GameConstants.CONFIGURATION_MAP.get(GameConstants.MINIMUM_NUM_OF_PLAYERS_NEEDED_KEY)) {
					endGame(gi);					
				}
				else 
				{
					gameInstanceService.saveOrUpdate(gi);
					loadNextQuestion(gi);
				}

			}
		}
	}
	
	public static void endGame(GameInstance gi) {
		log.info("debug Game Done !!!");		
		gi.setStateToDone();
		User user =  gi.markGameWinner();
		if(user != null){
			int winningPoints = gi.getGameWinningPoints();
			gi.getPlayers().get(user.getId()).addPoints(winningPoints); 
			userPointsService.addPoints(user.getId(), winningPoints);
		}
		gameInstanceService.saveOrUpdate(gi);
		finishedGames.put(gi.getId(), gi);
		ongoingGames.remove(gi.getId());
	}
	
	private static void loadNextQuestion(GameInstance gi) {
		gi.resetCurrentQuestionWinnerAndBestTime();
		QuestionManager.attachQuestionToGameInstance(gi);
		log.info("----- New Question attached is -----------"
				+ gi.getCurrentQuestion().getId().toString());
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
					int noOfPlayers = 0;
					if(gi.getPlayers() != null)
						noOfPlayers = gi.getPlayers().size();
					gi.setBestTimeForCurrentQuestion(secondsTakenToRespond);
					gi.setCurrentQuestionWinner(new User(userId));
					prl.setNoOfPlayersBeaten(gi.getPlayers().size() -1);
					prl.setQuestionWinner(true);
				}
				prl.getPlayer().addPoints(prl.getPointsEarned());
				userPointsService.addPoints(userId, prl.getPointsEarned());
			}
			
			if(gi.haveAllPlayersResponded()){
				calculateScoresForPlayers(gi);
				
				}
		}
		return gi;
	}


}
