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
import com.core.domain.User;

@Entity
@Table(name="user_elo_rating")
public class UserEloRating  implements Serializable{

	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne(optional=false)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="no_of_questions_attempted", columnDefinition="int default 0",nullable=false )
	private int noOfQuestionsAttempted;
	
	@Column(name="elo_rating", columnDefinition="int default 1000", nullable=false)
	private int eloRating;

	public int getEloRating() {
		return eloRating;
	}

	public void setEloRating(int eloRating) {
		this.eloRating = eloRating;
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

	public int getNoOfQuestionsAttempted() {
		return noOfQuestionsAttempted;
	}

	public void setNoOfQuestionsAttempted(int noOfQuestionsAttempted) {
		this.noOfQuestionsAttempted = noOfQuestionsAttempted;
	}
	
	public boolean isUserRatingProvisional(){
		return this.noOfQuestionsAttempted <= GameConstants.PROVISIONAL_LIMIT_FOR_ELO_RATING;
	}
	
	public void incrementNoOfQuestionsAttempted(){
		this.noOfQuestionsAttempted++;
	}
}
