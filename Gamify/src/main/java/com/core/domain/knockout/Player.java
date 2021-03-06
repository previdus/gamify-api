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
	
	@OneToOne(optional=true)
    @JoinColumn(name="player_rating_id")
	private PlayerEloRating playerEloRating;
	
	public PlayerEloRating getPlayerEloRating() {
		return playerEloRating;
	}
	public void setPlayerEloRating(PlayerEloRating playerEloRating) {
		this.playerEloRating = playerEloRating;
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
	
	
}
