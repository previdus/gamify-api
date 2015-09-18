package com.core.domain.knockout;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.core.constants.GameConstants;
import com.core.domain.User;

@Entity
@Table(name="player")
public class Player  implements Serializable{

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="user_id")
	private User user;
	@Column(name="no_of_life")
	private int noOfLife;
	
	@Column(name="no_of_questions_answered", nullable = false, columnDefinition="int default 0")
	private int noOfQuestionsAnswered;
	
	@Column(name="elo_rating", columnDefinition="int default 1000")
	private int eloRating;
	
	
	
	public int getNoOfQuestionsAnswered() {
		return noOfQuestionsAnswered;
	}
	public void setNoOfQuestionsAnswered(int noOfQuestionsAnswered) {
		this.noOfQuestionsAnswered = noOfQuestionsAnswered;
	}
	public int getEloRating() {
		return eloRating;
	}
	public void setEloRating(int eloRating) {
		this.eloRating = eloRating;
	}

	private transient long playerJoinTime;
	private transient int noOfPollsSoFar;
	
	private transient long   numOfExpectedPollsSincePlayerJoined;
	
	public long getNumOfExpectedPollsSincePlayerJoined() {
		return numOfExpectedPollsSincePlayerJoined;
	}
	public void setNumOfExpectedPollsSincePlayerJoined(
			long numOfExpectedPollsSincePlayerJoined) {
		this.numOfExpectedPollsSincePlayerJoined = numOfExpectedPollsSincePlayerJoined;
	}

	@JsonIgnore
	@ManyToOne(optional=false)
	@JoinColumn(name="game_instance_id")
	private GameInstance gameInstance;
	
	
	
	
	public long getPlayerJoinTime() {
		return playerJoinTime;
	}
	public void setPlayerJoinTime(long playerJoinTime) {
		this.playerJoinTime = playerJoinTime;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public GameInstance fetchAssociatedGameInstance() {
		return gameInstance;
	}
	public void setGameInstance(GameInstance gameInstance) {
		this.gameInstance = gameInstance;
	}
	public int getNoOfPollsSoFar() {
		return noOfPollsSoFar;
	}
	public void setNoOfPollsSoFar(int noOfPollsSoFar) {
		this.noOfPollsSoFar = noOfPollsSoFar;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getNoOfLife() {
		return noOfLife;
	}
	public void setNoOfLife(int noOfLife) {
		this.noOfLife = noOfLife;
	}
	
	public void incrementPollCount(){
		noOfPollsSoFar++;
	}
	
	public void resetPollCount(){
		noOfPollsSoFar = 0;
	}
	
	public boolean isProvisional(){
		return this.noOfQuestionsAnswered <= GameConstants.PROVISIONAL_LIMIT_FOR_ELO_RATING;
	}
	
}
