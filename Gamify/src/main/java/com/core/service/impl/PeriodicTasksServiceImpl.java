package com.core.service.impl;

import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.constants.GameConstants;
import com.core.service.PeriodicTasksService;
import com.core.service.threads.AutomaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTimeService;
import com.core.service.threads.CheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAliveService;
import com.core.service.threads.EmptyDoneGamesFromPlayerGameMapQueueService;
import com.core.service.threads.FlushStaleGamesFromQueuesService;
import com.core.service.threads.InspectAllQueuesService;
import com.core.service.threads.MoveFromOngoingToDoneService;
import com.core.service.threads.MoveFromReadyToOngoingQueueService;
import com.core.service.threads.MoveFromWaitingToReadyqueueService;
import com.core.service.threads.StoreFinishedGamesAndEmptyQueueService;
import com.core.service.threads.UpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinnerService;

@Service("periodicTasksService")
public class PeriodicTasksServiceImpl implements PeriodicTasksService {

	private static final Logger log = LoggerFactory
			.getLogger(PeriodicTasksService.class);

	private static Boolean threadStarted = false;

	private static final int NUM_OF_EXECUTORS = 8;

	private static ScheduledExecutorService[] executors = new ScheduledExecutorService[NUM_OF_EXECUTORS];

	@Autowired
	private  MoveFromWaitingToReadyqueueService moveFromWaitingToReadyQueueService;

	@Autowired
	private  MoveFromReadyToOngoingQueueService moveFromReadyToOngoingQueueService;
	
	@Autowired
	private  UpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinnerService 
	updateOngoingGamesQueueWithNextQuestionAndToCalculateWinnerService;
	
	@Autowired
	private  MoveFromOngoingToDoneService moveFromOngoingToDoneService;

	@Autowired
	private  FlushStaleGamesFromQueuesService flushStaleGamesFromQueuesService;
	
	@Autowired
	private  StoreFinishedGamesAndEmptyQueueService storeFinishedGamesAndEmptyQueueService;

	@Autowired
	private  EmptyDoneGamesFromPlayerGameMapQueueService emptyDoneGamesFromPlayerGameMapQueueService;

	@Autowired
	private  CheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAliveService 
	checkOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAliveService;

	
	@Autowired
	private  AutomaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTimeService
	automaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTimeService;
	

	@Autowired
	private  InspectAllQueuesService inspectAllQueuesService;
	

	

	@PostConstruct
	public  void startDaemonQueueManager() {
		if (threadStarted)
			return;

		for (int i = 0; i < NUM_OF_EXECUTORS; i++) {
			executors[i] = Executors.newSingleThreadScheduledExecutor();

		}
		executors[0]
				.scheduleAtFixedRate(
						moveFromWaitingToReadyQueueService,
						0,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES,
						TimeUnit.SECONDS);
		executors[1]
				.scheduleAtFixedRate(
						moveFromReadyToOngoingQueueService,
						1,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES,
						TimeUnit.SECONDS);
		executors[2]
				.scheduleAtFixedRate(
						updateOngoingGamesQueueWithNextQuestionAndToCalculateWinnerService,
						4,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_CHECKING_FOR_QUESTION_RESPONSES,
						TimeUnit.SECONDS);
//		executors[3]
//				.scheduleAtFixedRate(
//						flushStaleGamesFromQueuesService,
//						6,
//						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_FLUSHING_STALE_GAMES_TO_EXPIRED_GAMES_QUEUE,
//						TimeUnit.SECONDS);
		executors[3]
				.scheduleAtFixedRate(
						moveFromOngoingToDoneService,
						8,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES,
						TimeUnit.SECONDS);
		executors[4]
				.scheduleAtFixedRate(
						checkOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAliveService,
						10,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_POLLING_ALL_QUEUES_FOR_PLAYERS_STILL_ALIVE,
						TimeUnit.SECONDS);
		executors[5]
				.scheduleAtFixedRate(
						inspectAllQueuesService,
						0,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_POLLING_ALL_QUEUES,
						TimeUnit.SECONDS);
		executors[6]
				.scheduleAtFixedRate(
						storeFinishedGamesAndEmptyQueueService,
						5,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_EMPTYING_FINISHED_GAMES_QUEUES,
						TimeUnit.SECONDS);
		
		executors[7]
				.scheduleAtFixedRate(
						automaticallyDefaultToWrongOptionForPlayersWhoseResponseWasnotRecievedOnTimeService,
						5,
						GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES,
						TimeUnit.SECONDS);
		
		// executor[8].scheduleAtFixedRate(emptyDoneGamesFromPlayerGameMapQueue,
		// 6,
		// GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES,
		// TimeUnit.SECONDS);

		threadStarted = true;
	}
	
	
	
	

}
