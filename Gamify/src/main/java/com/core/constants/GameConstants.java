package com.core.constants;

import java.util.HashMap;
import java.util.Map;

public class GameConstants {

	public static final String SESSION_VARIABLE_LOGGEDIN_USER = "loggedInUser";
	public static final String SESSION_VARIABLE_LOGGEDIN_USER_RESULT = "loggedInUserResult";

	public static Long GAME_INSTANCE_ID = 1L;
	
	//move to db
	public static final String NUM_OF_LIVES_KEY = "numOfLives";
	private final static short NUM_OF_LIVES = 5;

	//move to db
	public static final String MINIMUM_NUM_OF_PLAYERS_NEEDED_KEY = "minPlayers";
	private final static short MINIMUM_NUM_OF_PLAYERS_NEEDED = 2;
	
	//move to db
	public static final String MAXIMUM_NUM_OF_PLAYERS_NEEDED_KEY =  "maxPlayers";
	private final static short MAXIMUM_NUM_OF_PLAYERS_NEEDED = 5;
	
	
	//move to db
	public static final String NUM_OF_TOP_PLAYERS_TO_DISPLAY_ON_LEADERBOARD_KEY = "numOfTopPlayersDisplayedOnLeaderBoard"; 
	private static final Integer NUM_OF_TOP_PLAYERS_TO_DISPLAY_ON_LEADERBOARD = 5;

	public static enum GAME_DIFFICULTY_LEVEL {
		EASY, MEDIIUM, HARD
	};

	public static enum GAME_STATE {
		NEW, WAITING, READY, ONGOING, DONE, EXPIRED
	};

	

	public static final int SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES = 10 ;

	public static final int SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_CHECKING_FOR_QUESTION_RESPONSES = 10 ;

	public static final int SECONDS_TO_WAIT_FOR_PLAYERS_BEFORE_BEGINNING_GAME = 10 ;

	public static final int SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_EMPTYING_FINISHED_GAMES_QUEUES = 60 ;

	public static final int SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_FLUSHING_STALE_GAMES_TO_EXPIRED_GAMES_QUEUE = 120 ;

	public static final int SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_POLLING_ALL_QUEUES = 30 ;
	
	public static final int SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_POLLING_ALL_QUEUES_FOR_PLAYERS_STILL_ALIVE = 10 ;

	//move to db
	public static final String PLAYER_IN_GAME_POLL_TIME_KEY =  "playerInGamePollTime";
	private static final long PLAYER_IN_GAME_POLL_TIME = 5000 ;

	//move to db
	public static final String MINIMUM_NUMBER_OF_POLLS_MISSED_BY_PLAYER_BEFORE_DECIDING_TO_REMOVE_PLAYER_FROM_GAME_KEY = "minNumOfPollsMissedByPlayerBeforePlayerRemoved"; 
	private static final int MINIMUM_NUMBER_OF_POLLS_MISSED_BY_PLAYER_BEFORE_DECIDING_TO_REMOVE_PLAYER_FROM_GAME = 3 ;

	
	public static final long MILLISECONDS_EVERY_QUESTION_IS_TIMED_FOR = 120000 ;
	
	//move to db
	public static final String EXTRA_TIME_NEEDED_TO_WAIT_BEFORE_AUTO_RESPOND_TO_UNANSWERED_QUESTION_KEY =  "extraTimeNeededToWaitBeforeAutoRespondToUnansweredQuestion";
	private static final int  EXTRA_TIME_NEEDED_TO_WAIT_BEFORE_AUTO_RESPOND_TO_UNANSWERED_QUESTION = 10;
	
	//ELO_RATING
	//move to db	
	public static final String PROVISIONAL_LIMIT_FOR_ELO_RATING_KEY =  "provisionalLimitForEloRating";
	private static final int PROVISIONAL_LIMIT_FOR_ELO_RATING = 25;
	
	//move to db
	public static final String KVALUE_FOR_ELO_RATING_KEY =  "kvalyeForEloRating";
	private final static short KVALUE_FOR_ELO_RATING = 16;
	
	public static final int LOSER_NUMBER_FROM_WHICH_EXPECTANCY_WILL_BE_SUBTRACTED_FOR_ELO_RATING = 0;
	
	public static final int WINNER_NUMBER_FROM_WHICH_EXPECTANCY_WILL_BE_SUBTRACTED_FOR_ELO_RATING = 1;
	
	public static final int DEFAULT_INITIAL_USER_ELO_RATING = 1000;
	
	// keep -1 if you dont want baut to be added
	//move to db
	public static final String ADD_BOT_USER_AFTER_WAITING_MILLISECONDS_KEY = "addBotUserAterWaitingMilliseconds"; 
	private static final int ADD_BOT_USER_AFTER_WAITING_MILLISECONDS = 20000;
	
	//move to db
	public static final String IS_EMAIL_VERIFICATION_MANDATORY_KEY =  "isEmailVerificationMandatory";
	private static final boolean IS_EMAIL_VERIFICATION_MANDATORY = false;
	
	//move to db
	public static final String SEND_EMAIL_VERIFICATION_EMAIL_ON_LOGIN_KEY = "sendEmailVerificationEmailOnLogin"; 
	private static final boolean SEND_EMAIL_VERIFICATION_EMAIL_ON_LOGIN = true;
	
	
	public static final Map<String, Object> CONFIGURATION_MAP = new HashMap<String, Object>();
	
	static{
		CONFIGURATION_MAP.put(SEND_EMAIL_VERIFICATION_EMAIL_ON_LOGIN_KEY, SEND_EMAIL_VERIFICATION_EMAIL_ON_LOGIN);
		CONFIGURATION_MAP.put(IS_EMAIL_VERIFICATION_MANDATORY_KEY,IS_EMAIL_VERIFICATION_MANDATORY);
		CONFIGURATION_MAP.put(ADD_BOT_USER_AFTER_WAITING_MILLISECONDS_KEY,ADD_BOT_USER_AFTER_WAITING_MILLISECONDS);
		CONFIGURATION_MAP.put(KVALUE_FOR_ELO_RATING_KEY,KVALUE_FOR_ELO_RATING);
		CONFIGURATION_MAP.put(PROVISIONAL_LIMIT_FOR_ELO_RATING_KEY,PROVISIONAL_LIMIT_FOR_ELO_RATING);
		CONFIGURATION_MAP.put(EXTRA_TIME_NEEDED_TO_WAIT_BEFORE_AUTO_RESPOND_TO_UNANSWERED_QUESTION_KEY,
				EXTRA_TIME_NEEDED_TO_WAIT_BEFORE_AUTO_RESPOND_TO_UNANSWERED_QUESTION);
		CONFIGURATION_MAP.put(MINIMUM_NUMBER_OF_POLLS_MISSED_BY_PLAYER_BEFORE_DECIDING_TO_REMOVE_PLAYER_FROM_GAME_KEY,
				MINIMUM_NUMBER_OF_POLLS_MISSED_BY_PLAYER_BEFORE_DECIDING_TO_REMOVE_PLAYER_FROM_GAME);
		CONFIGURATION_MAP.put(PLAYER_IN_GAME_POLL_TIME_KEY,PLAYER_IN_GAME_POLL_TIME);
		CONFIGURATION_MAP.put(NUM_OF_TOP_PLAYERS_TO_DISPLAY_ON_LEADERBOARD_KEY,NUM_OF_TOP_PLAYERS_TO_DISPLAY_ON_LEADERBOARD);
		CONFIGURATION_MAP.put(NUM_OF_LIVES_KEY,NUM_OF_LIVES);
		CONFIGURATION_MAP.put(MINIMUM_NUM_OF_PLAYERS_NEEDED_KEY,MINIMUM_NUM_OF_PLAYERS_NEEDED);
		CONFIGURATION_MAP.put(MAXIMUM_NUM_OF_PLAYERS_NEEDED_KEY,MAXIMUM_NUM_OF_PLAYERS_NEEDED);
	}

}
