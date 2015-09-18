package com.core.constants;

public class GameConstants {

	public static final String SESSION_VARIABLE_LOGGEDIN_USER = "loggedInUser";
	public static final String SESSION_VARIABLE_LOGGEDIN_USER_RESULT = "loggedInUserResult";

	public static Long GAME_INSTANCE_ID = 1L;
	public final static short NUM_OF_LIVES = 5;
	public final static short MINIMUM_NUM_OF_PLAYERS_NEEDED = 2;
	public final static short MAXIMUM_NUM_OF_PLAYERS_NEEDED = 5;
	
	public static final Integer NUM_OF_TOP_PLAYERS_TO_DISPLAY_ON_LEADERBOARD = 5;

	public static enum GAME_DIFFICULTY_LEVEL {
		EASY, MEDIIUM, HARD
	};

	public static enum GAME_STATE {
		NEW, WAITING, READY, ONGOING, DONE, EXPIRED
	};

	public static final int CONSTANT_MULTIPLIER = 1;

	public static final int SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES = 10 * CONSTANT_MULTIPLIER;

	public static final int SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_CHECKING_FOR_QUESTION_RESPONSES = 10 * CONSTANT_MULTIPLIER;

	public static final int SECONDS_TO_WAIT_FOR_PLAYERS_BEFORE_BEGINNING_GAME = 10 * CONSTANT_MULTIPLIER;

	public static final int SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_EMPTYING_FINISHED_GAMES_QUEUES = 60 * CONSTANT_MULTIPLIER;

	public static final int SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_FLUSHING_STALE_GAMES_TO_EXPIRED_GAMES_QUEUE = 30 * CONSTANT_MULTIPLIER;

	public static final int SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_POLLING_ALL_QUEUES = 30 * CONSTANT_MULTIPLIER;
	
	public static final int SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_POLLING_ALL_QUEUES_FOR_PLAYERS_STILL_ALIVE = 10 * CONSTANT_MULTIPLIER;

	public static final long PLAYER_IN_GAME_POLL_TIME = 5000 * CONSTANT_MULTIPLIER;

	public static final int MINIMUM_NUMBER_OF_POLLS_MISSED_BY_PLAYER_BEFORE_DECIDING_TO_REMOVE_PLAYER_FROM_GAME = 3 * CONSTANT_MULTIPLIER;

	public static final long MILLISECONDS_EVERY_QUESTION_IS_TIMED_FOR = 120000 * CONSTANT_MULTIPLIER;
	
	public static final int  EXTRA_TIME_NEEDED_TO_WAIT_BEFORE_AUTO_RESPOND_TO_UNANSWERED_QUESTION = 10;
	
	public static final int PROVISIONAL_LIMIT_FOR_ELO_RATING = 25;
	
	public final static double KVALUE_FOR_ELO_RATING = 32;
	
	public static final int INITIAL_ELO_RATING = 1000;
	
	// keep -1 if you dont want baut to be added
	public static final int ADD_BOUT_USER_AFTER_WAITING_MILLISECONDS = -1;

}
