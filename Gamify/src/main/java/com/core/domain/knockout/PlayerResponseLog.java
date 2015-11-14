package com.core.domain.knockout;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.core.constants.GameConstants;
import com.core.domain.Option;
import com.core.domain.User;

@Entity
@Table(name="player_response_log")
public class PlayerResponseLog implements Comparable<PlayerResponseLog>, Serializable{
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="player_id" , nullable=true)
	private Player player;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="response_option_id")
	private Option response;
	
	@Column(name="time_taken_to_answer")
    private Long timeTakenToAnswer;
	
	@Column(name="time_at_which_player_responded")
    private Long timeAtWhichPlayerResponded = System.currentTimeMillis();
	
	@Column(name="free_text_response")
	private String freeTextResponse;
	
	@Column(name="game_id")
	private long gameId;
	
	
	public Long getTimeAtWhichPlayerResponded() {
		return timeAtWhichPlayerResponded;
	}



	public void setTimeAtWhichPlayerResponded(Long timeAtWhichPlayerResponded) {
		this.timeAtWhichPlayerResponded = timeAtWhichPlayerResponded;
	}


	private Integer rank;
	
	@Column(name="no_of_players_beaten" ,columnDefinition="int default 0")
    private int noOfPlayersBeaten;
	
	
	@Column(name="points_earned",columnDefinition="int default 0")
	private int pointsEarned;
	
	@Column(name="question_id", columnDefinition="int default 1")
	private long questionId;
	
	@Column(name="is_response_correct", columnDefinition="boolean default false")
	private boolean responseCorrect;
		
	@Column(name="is_question_winner", columnDefinition="boolean default false")
	private boolean questionWinner;
	
	@JsonIgnore
	@ManyToOne(optional=false)
	@JoinColumn(name="previous_question_log_id")
    private PreviousQuestionLog previousQuestionLog;
	
	
	public PlayerResponseLog() {
		super();
	}
	
	
	
	public PlayerResponseLog(long gameId, Player player, User user, Option response, String freeTextResponse,
			Long timeTakenToAnswer,  long questionId) {
		super();
		this.gameId = gameId;
		this.player = player;
		this.user = user;
		this.response = response;
		this.timeTakenToAnswer = timeTakenToAnswer;
		this.questionId = questionId;
		this.freeTextResponse = freeTextResponse;
		markResponse(response);
	}



//	public PlayerResponseLog(User user, Option response,
//			Long timeTakenToAnswer, Integer rank) {
//		super();
//		this.user = user;
//		this.response = response;
//		this.timeTakenToAnswer = timeTakenToAnswer;
//		this.rank = rank;
//		
//		markResponse(response);
//	}
//	
	
	
	private void markResponse(Option response){
		if(response != null && response.getId() == -1)
			this.response = null;
	}
	
	public int getPointsEarned() {
		return pointsEarned;
	}



	private void setPointsEarned(int pointsEarned) {
		this.pointsEarned = pointsEarned;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public PreviousQuestionLog fetchAssociatedPreviousQuestionLog() {
		return previousQuestionLog;
	}
	public void setPreviousQuestionLog(PreviousQuestionLog previousQuestionLog) {
		this.previousQuestionLog = previousQuestionLog;
	}
	public Option getResponse() {
		return response;
	}
	public void setResponse(Option response) {
		this.response = response;
		markResponse(response);
	}
	
	
	public Integer getRank() {
		return rank;
	}
	public Long getTimeTakenToAnswer() {
		return timeTakenToAnswer;
	}
	public void setTimeTakenToAnswer(Long timeTakenToAnswer) {
		this.timeTakenToAnswer = timeTakenToAnswer;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	
	public int compareTo(PlayerResponseLog prl){
		if(this.getTimeTakenToAnswer() > prl.getTimeTakenToAnswer()){
			return 1;
		}else if(this.getTimeTakenToAnswer() < prl.getTimeTakenToAnswer()){
			return -1;
		}else{
			return 0;
		}
	}



	public long getQuestionId() {
		return questionId;
	}



	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}



	public boolean isResponseCorrect() {
		return responseCorrect;
	}



	public void setResponseCorrect(boolean responseCorrect) {
		this.responseCorrect = responseCorrect;
		this.pointsEarned = this.pointsEarned + 100;
		this.player.addPoints(100);
	}



	public boolean isQuestionWinner() {
		return questionWinner;
	}



//	public void setQuestionWinner(boolean questionWinner) {
//		this.questionWinner = questionWinner;
//	}



	public int getNoOfPlayersBeaten() {
		return noOfPlayersBeaten;
	}



	public void setNoOfPlayersBeaten(int noOfPlayersBeaten) {
		this.noOfPlayersBeaten = noOfPlayersBeaten;
		this.questionWinner = true;
		if(noOfPlayersBeaten > 0)
			this.pointsEarned = this.pointsEarned + (noOfPlayersBeaten * 100);
		this.player.addPoints(noOfPlayersBeaten * 100);
	}


	public PreviousQuestionLog getPreviousQuestionLog() {
		return previousQuestionLog;
	}
	
	public String getFreeTextResponse() {
		return freeTextResponse;
	}

	public void setFreeTextResponse(String freeTextResponse) {
		this.freeTextResponse = freeTextResponse;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public void markResponseAsWrong() {
		if(GameConstants.LMS_NEGATIVE_MARKING_ENABLED){
		if(this.response == null && (this.freeTextResponse == null || this.freeTextResponse.trim().length() == 0))
			this.pointsEarned = -25;
		else
			this.pointsEarned = -50;
		}
		this.player.addPoints(this.pointsEarned);
		
	}
	
}
