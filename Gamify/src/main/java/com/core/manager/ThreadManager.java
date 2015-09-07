package com.core.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.core.constants.GameConstants;
import com.core.threads.PeriodicTaskToEmptyDoneGamesFromPlayerGameMapQueue;
import com.core.threads.PeriodicTaskToAutomaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTime;
import com.core.threads.PeriodicTaskToCheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAlive;
import com.core.threads.PeriodicTaskToFlushStaleGamesFromQueues;
import com.core.threads.PeriodicTaskToMoveFromOngoingToDone;
import com.core.threads.PeriodicTaskToMoveFromReadyToOngoingqueue;
import com.core.threads.PeriodicTaskToMoveFromWaitingToReadyqueue;
import com.core.threads.PeriodicTaskToUpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinner;
import com.core.threads.PeriodicTaskToInspectAllQueues;
import com.core.threads.PeriodicTaskToStoreFinishedGamesAndEmptyQueue;

@Component
public class ThreadManager {

	private static final Logger log = LoggerFactory
			.getLogger(ThreadManager.class);

	private static Boolean threadStarted = false;

	private static final int NUM_OF_EXECUTORS = 9;

	private static ScheduledExecutorService[] executors = new ScheduledExecutorService[NUM_OF_EXECUTORS];

	
	

	static {
		for (int i = 0; i < NUM_OF_EXECUTORS; i++) {
			executors[i] = Executors.newSingleThreadScheduledExecutor();

		}
	}

	public static void startDaemonQueueManager() {
		if (threadStarted)
			return;

		executors[0]
				.scheduleAtFixedRate(
						periodicTaskToMoveFromWaitingToReadyqueue,
						0,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES,
						TimeUnit.SECONDS);
		executors[1]
				.scheduleAtFixedRate(
						periodicTaskToMoveFromReadyToOngoingqueue,
						1,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES,
						TimeUnit.SECONDS);
		executors[2]
				.scheduleAtFixedRate(
						periodicTaskToUpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinner,
						4,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_CHECKING_FOR_QUESTION_RESPONSES,
						TimeUnit.SECONDS);
		executors[3]
				.scheduleAtFixedRate(
						periodicTaskToFlushStaleGamesFromQueues,
						6,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_FLUSHING_STALE_GAMES_TO_EXPIRED_GAMES_QUEUE,
						TimeUnit.SECONDS);
		executors[4]
				.scheduleAtFixedRate(
						periodicTaskToMoveFromOngoingToDone,
						8,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES,
						TimeUnit.SECONDS);
		executors[5]
				.scheduleAtFixedRate(
						periodicTaskToCheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAlive,
						10,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_POLLING_ALL_QUEUES_FOR_PLAYERS_STILL_ALIVE,
						TimeUnit.SECONDS);
		executors[6]
				.scheduleAtFixedRate(
						inspectAllQueues,
						0,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_POLLING_ALL_QUEUES,
						TimeUnit.SECONDS);
		executors[7]
				.scheduleAtFixedRate(
						storeFinishedGamesAndEmptyQueue,
						5,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_EMPTYING_FINISHED_GAMES_QUEUES,
						TimeUnit.SECONDS);
		
		executors[8]
				.scheduleAtFixedRate(
						periodicTaskToAutomaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTime,
						5,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES,
						TimeUnit.SECONDS);
		
		// executor[8].scheduleAtFixedRate(emptyDoneGamesFromPlayerGameMapQueue,
		// 6,
		// GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES,
		// TimeUnit.SECONDS);

		threadStarted = true;
	}
	
	private static PeriodicTaskToMoveFromWaitingToReadyqueue periodicTaskToMoveFromWaitingToReadyqueue = new PeriodicTaskToMoveFromWaitingToReadyqueue();

	private static PeriodicTaskToMoveFromReadyToOngoingqueue periodicTaskToMoveFromReadyToOngoingqueue = new PeriodicTaskToMoveFromReadyToOngoingqueue();
	
	private static PeriodicTaskToUpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinner periodicTaskToUpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinner = new PeriodicTaskToUpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinner();
	
	private static PeriodicTaskToMoveFromOngoingToDone periodicTaskToMoveFromOngoingToDone = new PeriodicTaskToMoveFromOngoingToDone();

	private static PeriodicTaskToFlushStaleGamesFromQueues periodicTaskToFlushStaleGamesFromQueues =  new PeriodicTaskToFlushStaleGamesFromQueues();
	
	private static PeriodicTaskToStoreFinishedGamesAndEmptyQueue storeFinishedGamesAndEmptyQueue = new PeriodicTaskToStoreFinishedGamesAndEmptyQueue();

	private static PeriodicTaskToEmptyDoneGamesFromPlayerGameMapQueue emptyDoneGamesFromPlayerGameMapQueue = new PeriodicTaskToEmptyDoneGamesFromPlayerGameMapQueue();

	private static PeriodicTaskToCheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAlive 
	periodicTaskToCheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAlive = 
			new PeriodicTaskToCheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAlive();

	
	private static PeriodicTaskToAutomaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTime
	periodicTaskToAutomaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTime = 
	new PeriodicTaskToAutomaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTime();
	

	private static PeriodicTaskToInspectAllQueues inspectAllQueues = new PeriodicTaskToInspectAllQueues();
	
	

}
