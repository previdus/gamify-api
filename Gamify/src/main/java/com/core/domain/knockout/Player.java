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

import com.core.domain.User;

@Entity
@Table(name="player")
public class Player  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="user_id")
	private User user;
	@Column(name="no_of_life")
	private int noOfLife;
	
	@Column(name="points_won" ,columnDefinition="int default 0")
	private int pointsWon=0;

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
	
	
	public int getPointsWon() {
		return pointsWon;
	}
	public void setPointsWon(int pointsWon) {
		this.pointsWon = pointsWon;
	}
	
	public void addPoints(int pointsWon) {
		this.pointsWon = this.pointsWon + pointsWon;
	}
	
	
	
	
	
	
	
}
