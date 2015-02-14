package com.core.manager;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.constants.GameConstants;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.Player;
import com.core.service.GameInstanceService;






@Component
public class ThreadManager {
	
	private static final Logger log = LoggerFactory.getLogger(ThreadManager.class);
	
	private static Boolean threadStarted = false;
	
	private static final int NUM_OF_EXECUTORS = 8;
	
	private static ScheduledExecutorService[] executors = new ScheduledExecutorService[NUM_OF_EXECUTORS];
	
	private static GameInstanceService gameInstanceService;
	
	  @Autowired(required = true)
	  public void setGameInstanceService(GameInstanceService gameInstanceService) {
		  ThreadManager.gameInstanceService = gameInstanceService;
	  }
	
	static{
		for(int i = 0; i < NUM_OF_EXECUTORS;i++){
			executors[i] = Executors.newSingleThreadScheduledExecutor();
			
		}
	}
	

	public static void startDaemonQueueManager(){
		if(threadStarted) return;
		
		
		executors[0].scheduleAtFixedRate(periodicTaskToMoveFromWaitingToReadyqueue, 0, GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES, TimeUnit.SECONDS);
		executors[1].scheduleAtFixedRate(periodicTaskToMoveFromReadyToOngoingqueue, 1, GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES, TimeUnit.SECONDS);
		executors[2].scheduleAtFixedRate(periodicTaskToUpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinner, 4, GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_CHECKING_FOR_QUESTION_RESPONSES, TimeUnit.SECONDS);
		executors[3].scheduleAtFixedRate(periodicTaskToFlushStaleGamesFromQueues, 6, GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_FLUSHING_STALE_GAMES_TO_EXPIRED_GAMES_QUEUE, TimeUnit.SECONDS);
		executors[4].scheduleAtFixedRate(periodicTaskToMoveFromOngoingToDone, 8, GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES, TimeUnit.SECONDS);
		executors[5].scheduleAtFixedRate(periodicTaskToCheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAlive, 10,GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_POLLING_ALL_QUEUES, TimeUnit.SECONDS);
		executors[6].scheduleAtFixedRate(inspectAllQueues, 0,GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_POLLING_ALL_QUEUES, TimeUnit.SECONDS);
		executors[7].scheduleAtFixedRate(storeFinishedGamesAndEmptyQueue, 5, GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_EMPTYING_FINISHED_GAMES_QUEUES, TimeUnit.SECONDS);		
		//executor[8].scheduleAtFixedRate(emptyDoneGamesFromPlayerGameMapQueue, 6, GameConstants.SECONDS_THE_THREAD_SHOULD_WAIT_BEFORE_UPDATING_THE_QUEUES, TimeUnit.SECONDS);
				
		
		threadStarted = true;
	}
	
	
	
	
	
	
	
	private static Runnable periodicTaskToMoveFromWaitingToReadyqueue = new Runnable() {
	    public void run() {
	    	
	    	log.info("1) periodicTaskToMoveFromWaitingToReadyqueue");
	    	
	    	//move from waiting to ready
	    	try{
		    	for(Long examSectionId : GameQueueManager.waitingForMorePlayersToJoinGames.keySet()){
	
					GameInstance gi = GameQueueManager.waitingForMorePlayersToJoinGames.get(examSectionId);
					if(gi.getStartWaitTime() == 0){
						gi.setStartWaitTime(System.currentTimeMillis());				
					}
					else{
					    if((System.currentTimeMillis() - gi.getStartWaitTime())/1000 >= GameConstants.SECONDS_TO_WAIT_FOR_PLAYERS_BEFORE_BEGINNING_GAME){
					    	gi.setState(GameConstants.GAME_STATE.READY);
					    	log.info("before moving to ready games");
					    	GameQueueManager.readyGames.put(gi.getId(), gi);
					    	GameQueueManager.waitingForMorePlayersToJoinGames.remove(examSectionId);
					    }
					}
				}
	    	}
	    	catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	};
	
	private static Runnable periodicTaskToMoveFromReadyToOngoingqueue = new Runnable() {
	    public void run() {
	    	log.info("2) running the periodicTaskToMoveFromReadyToOngoingqueue thread");
	    	//from ready to ongoing
			try{
				for(Long gameInstanceId : GameQueueManager.readyGames.keySet()){
	
					GameInstance gi = GameQueueManager.readyGames.get(gameInstanceId);
					gi.setState(GameConstants.GAME_STATE.ONGOING);
					gi.setStartTime(System.currentTimeMillis());
					log.info("before moving from ready to ongoing game+s");
					QuestionManager.attachQuestionToGameInstance(gi);
					GameQueueManager.ongoingGames.put(gi.getId(),gi);
					GameQueueManager.readyGames.remove(gameInstanceId);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
	    }
	};
	
	private static Runnable periodicTaskToUpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinner = new Runnable() {
	    public void run(){			
	    	log.info("3) periodicTaskToUpdateOngoingGamesQueueWithNextQuestionAndToCalculateWinner");
	    	try{
				for(GameInstance gi : GameQueueManager.ongoingGames.values()){	
					try{
					GameQueueManager.calculateScoresForPlayers(gi);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
	    	}catch(Exception ex){
	    		ex.printStackTrace();
	    	}
	    }
	};
	
	
	private static Runnable periodicTaskToMoveFromOngoingToDone = new Runnable() {
	    public void run() {
	    	
	    	log.info("4) periodicTaskToMoveFromOngoingToDone");
			
			//from ongoing to done
			try{
				for(Long gameInstanceId : GameQueueManager.ongoingGames.keySet()){			
					GameInstance gi = GameQueueManager.ongoingGames.get(gameInstanceId);
					if(gi.getPlayers().size() <= 1){
						//dump the previous question log to database;
						//end of dump
						gi.setStateToDone();
						//gi.markGameWinner();
						GameQueueManager.finishedGames.put(gi.getId(),gi);
						GameQueueManager.ongoingGames.remove(gameInstanceId);					
					}
					
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
	    }
	};
	
	
	
	

	
	private static Runnable periodicTaskToFlushStaleGamesFromQueues = new Runnable() {
	    public void run() {
	    	try{
	    	log.info("5) periodicTaskToFlushStaleGamesFromQueues");
			
			//flush playerGameMap
			
//			for(Long playerId: GameQueueManager.playerGameMap.keySet()){
//				GameInstance gi = GameQueueManager.playerGameMap.get(playerId);
//				if(gi.getState().equals(GameConstants.GAME_STATE.DONE)){
//					GameQueueManager.playerGameMap.remove(playerId);
//				}
//			}
			
			/*
			 * Make sure games are moved to expired state if they become stale
			 */
			
			//for new games
			for(Long examSectionId : GameQueueManager.newGames.keySet()){
				GameInstance gi = GameQueueManager.newGames.get(examSectionId);
				if(gi.getPlayers()== null || gi.getPlayers().size() == 0){
					gi.setState(GameConstants.GAME_STATE.EXPIRED);
					GameQueueManager.newGames.remove(examSectionId);
					GameQueueManager.expriredGames.put(gi.getId(), gi);
				}
			}
			

			//for waiting games
			for(Long examSectionId : GameQueueManager.waitingForMorePlayersToJoinGames.keySet()){
				GameInstance gi = GameQueueManager.waitingForMorePlayersToJoinGames.get(examSectionId);
				if(gi.getPlayers()!= null ){
					if(gi.getPlayers().size() == 1){
						Player player = gi.getPlayers().get(0);					
						GameQueueManager.waitingForMorePlayersToJoinGames.remove(examSectionId);
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						GameQueueManager.expriredGames.put(gi.getId(),gi);
						GameInstance gameAlreadyReadyForThisPlayer = GameQueueManager.readyGames.get(gi.getId());
						if(gameAlreadyReadyForThisPlayer == null){
							GameQueueManager.createGameInstance(gi.getExamSection(), player.getUser());
						}
						else{
							GameQueueManager.playerGameMap.put(player.getUser().getId(), gameAlreadyReadyForThisPlayer);
							gameAlreadyReadyForThisPlayer.addPlayerFromAnotherGame(player);
						}
						
						
					}
					else 
						if(gi.getPlayers().size() == 0){
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						GameQueueManager.expriredGames.put(gi.getId(), gi);
						GameQueueManager.waitingForMorePlayersToJoinGames.remove(examSectionId);
					}
					
					
					
				}
				else {
					gi.setState(GameConstants.GAME_STATE.EXPIRED);
					GameQueueManager.expriredGames.put(gi.getId(), gi);
					GameQueueManager.waitingForMorePlayersToJoinGames.remove(examSectionId);
				}
				
			}
			
			//for ready games
			for(Long gameInstanceId : GameQueueManager.readyGames.keySet()){
				GameInstance gi = GameQueueManager.readyGames.get(gameInstanceId);
				if(gi.getPlayers()!= null ){
					if(gi.getPlayers().size() == 0){
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						GameQueueManager.expriredGames.put(gi.getId(), gi);
						GameQueueManager.readyGames.remove(gameInstanceId);
					}
				    else if(gi.getPlayers().size() < 2){
				    	gi.setState(GameConstants.GAME_STATE.WAITING);
				    	GameQueueManager.waitingForMorePlayersToJoinGames.put(gi.getId(), gi);
						GameQueueManager.readyGames.remove(gameInstanceId);						
					}
				}
				else{
					gi.setState(GameConstants.GAME_STATE.EXPIRED);
					GameQueueManager.expriredGames.put(gi.getId(), gi);
					GameQueueManager.readyGames.remove(gameInstanceId);
				}
			}
			
			//for ongoing games
			for(Long gameInstanceId: GameQueueManager.ongoingGames.keySet()){
				GameInstance gi = GameQueueManager.ongoingGames.get(gameInstanceId);
				if(gi.getPlayers()!= null ){
					if(gi.getPlayers().size() == 1){
						Player player = gi.getPlayers().get(0);
						GameQueueManager.ongoingGames.remove(gameInstanceId);
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						GameQueueManager.expriredGames.put(gameInstanceId, gi);
						GameInstance gameAlreadyReadyForThisPlayer = GameQueueManager.readyGames.get(gi.getId());
						if(gameAlreadyReadyForThisPlayer == null){
							GameQueueManager.createGameInstance(gi.getExamSection(), player.getUser());
						}
						else{
							GameQueueManager.playerGameMap.put(player.getUser().getId(), gameAlreadyReadyForThisPlayer);							
							gameAlreadyReadyForThisPlayer.addPlayerFromAnotherGame(player);
						}
					
						
					}
					else if(gi.getPlayers().size() == 0){
						gi.setState(GameConstants.GAME_STATE.EXPIRED);
						GameQueueManager.expriredGames.put(gameInstanceId, gi);
						GameQueueManager.ongoingGames.remove(gameInstanceId);
							
					}
					
				}
				else{
					gi.setState(GameConstants.GAME_STATE.EXPIRED);
					GameQueueManager.expriredGames.put(gi.getId(), gi);
					GameQueueManager.ongoingGames.remove(gameInstanceId);
				}
				
			}
			
	    }catch(Exception ex){
	    	ex.printStackTrace();
	    }
	    }
			
			
	};
	
	private static Runnable storeFinishedGamesAndEmptyQueue = new Runnable(){
		public void run(){
			try{
			GameInstance gameInstance = null;
			for(Long gameInstanceId: GameQueueManager.finishedGames.keySet()){	
			   gameInstance = GameQueueManager.finishedGames.get(gameInstanceId);
			   // save in db
			   log.info("Prev Question Log Size " + GameQueueManager.gameResponseLog.get(gameInstanceId).size());
			   gameInstance.setPreviousQuestionLogs(GameQueueManager.gameResponseLog.get(gameInstanceId));
			  
			   log.info("No of Loosers " + gameInstance.getLooserPlayers().size());
			   gameInstance.getPlayers().putAll(gameInstance.getLooserPlayers());
			   //gameInstance.markGameWinner();
			   gameInstanceService.saveOrUpdate(gameInstance);
			   GameQueueManager.gameResponseLog.remove(gameInstanceId);
			}
			GameQueueManager.finishedGames.clear();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
    
	};	
	
	private static Runnable emptyDoneGamesFromPlayerGameMapQueue = new Runnable(){
		public void run(){
	
			//Empty done games. IN real implementation the done games list will be stored in the database and then emptied from memory
			for(Long userId: GameQueueManager.playerGameMap.keySet()){
				GameInstance gi = GameQueueManager.playerGameMap.get(userId);
				/*if(GameConstants.GAME_STATE.DONE.equals(gi.getState()) || GameConstants.GAME_STATE.EXPIRED.equals(gi.getState())){
					
					GameQueueManager.playerGameMap.remove(userId);
				}*/
			}
		}
    
	};
	
	private static Runnable periodicTaskToCheckOnPlayersInNewWaitingReadyAndOngoingQueuesIfTheyAreStillAlive = new Runnable(){
		public void run(){
	
			//new games
			for(GameInstance gi: GameQueueManager.newGames.values()){
				removePlayerFromGameIfHisPollsAreMissedForALongTime(gi);				
			}
			
			//waiting games
			for(GameInstance gi: GameQueueManager.waitingForMorePlayersToJoinGames.values()){
				removePlayerFromGameIfHisPollsAreMissedForALongTime(gi);				
			}
			
			//ready games
			for(GameInstance gi: GameQueueManager.readyGames.values()){
				removePlayerFromGameIfHisPollsAreMissedForALongTime(gi);				
			}
			
			//ongoing games
			for(GameInstance gi: GameQueueManager.ongoingGames.values()){
				removePlayerFromGameIfHisPollsAreMissedForALongTime(gi);				
			}
		}
    
	};
	
	private static void removePlayerFromGameIfHisPollsAreMissedForALongTime(GameInstance gi){
		for(Player player:gi.getPlayers().values()){
			long numOfExpectedPollsSincePlayerJoined = (System.currentTimeMillis() - player.getPlayerJoinTime())/5000;
			if((numOfExpectedPollsSincePlayerJoined - player.getNoOfPollsSoFar()) > GameConstants.MINIMUM_NUMBER_OF_POLLS_MISSED_BY_PLAYER_BEFORE_DECIDING_TO_REMOVE_PLAYER_FROM_GAME){
				gi.removePlayer(player.getUser());
				GameQueueManager.playerGameMap.remove(player.getUser().getId());
			}
		}
	}
	
	
	
	private static Runnable inspectAllQueues = new Runnable(){
		public void run(){
			try{
			//new games
			log.info("New games");
			for(Long examSectionId: GameQueueManager.newGames.keySet()){
				System.out.print(examSectionId+",");
			}			

			//waiting games
			log.info("waiting games");
			for(Long examSectionId: GameQueueManager.waitingForMorePlayersToJoinGames.keySet()){
				System.out.print(examSectionId+",");
			}
			
			//ready games
			log.info("ready games");
			for(Long examSectionId: GameQueueManager.readyGames.keySet()){
				System.out.print(examSectionId+",");
			}
			//ongoing games
			log.info("Ongoing games");
			for(Long gameInstanceId: GameQueueManager.ongoingGames.keySet()){
				System.out.print(gameInstanceId+",");
			}
			//finished games
			log.info("finished games");
			for(Long gameInstanceId: GameQueueManager.finishedGames.keySet()){
				System.out.print(gameInstanceId+",");
			}
		    //expired games
			log.info("Expired games");
			for(Long gameInstanceId: GameQueueManager.expriredGames.keySet()){
				System.out.print(gameInstanceId+",");
			}
			//playerMap queue
			
			log.info("Player Game map");
			for(Long playerId: GameQueueManager.playerGameMap.keySet()){
				log.info(playerId+",");
				GameInstance gi = GameQueueManager.playerGameMap.get(playerId);
				log.info("num of players in gi with id:"+gi.getId()+" is ["+gi.getNumOfPlayers()+"]");
				log.info("the state of the game is:"+gi.getState().toString());
				for(Player player: gi.getPlayers().values()){
					log.info(" **************the number of lifes for player with id:"+player.getUser().getId()+" is :"+player.getNoOfLife());
				}
			}
			
			log.info("------------------------------------------");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		}
		
	};
	
	

}
