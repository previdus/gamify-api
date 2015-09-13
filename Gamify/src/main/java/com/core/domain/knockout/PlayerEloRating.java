package com.core.domain.knockout;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.core.constants.GameConstants;

@Entity
@Table(name="player_elo_rating")
public class PlayerEloRating  implements Serializable{

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(optional=false)
	@JoinColumn(name="player_id")
	private Player player;
	
	
	
	@Column(name="no_of_games_played", nullable = false, columnDefinition="int default 0")
	private int noOfGamesPlayed;
	
	@Column(name="rating",nullable =false, columnDefinition="int default 1000")
	private int rating;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public int getNoOfGamesPlayed() {
		return noOfGamesPlayed;
	}
	public void setNoOfGamesPlayed(int noOfGamesPlayed) {
		this.noOfGamesPlayed = noOfGamesPlayed;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	public boolean isProvisional(){
		return this.noOfGamesPlayed <= GameConstants.PROVISIONAL_GAMES_LIMIT_FOR_ELO_RATING;
	}
	
	
	
	
	
	
}
