package com.core.domain.knockout;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.core.constants.GameConstants;
import com.core.domain.User;

@Entity
@Table(name="user_elo_rating_topic")
public class UserEloRatingPerTopic  implements Serializable{

	@EmbeddedId
    private UserTopic userTopic;
	
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

	


	public UserTopic getUserTopic() {
		return userTopic;
	}

	public void setUserTopic(UserTopic userTopic) {
		this.userTopic = userTopic;
	}

	public int getNoOfQuestionsAttempted() {
		return noOfQuestionsAttempted;
	}

	public void setNoOfQuestionsAttempted(int noOfQuestionsAttempted) {
		this.noOfQuestionsAttempted = noOfQuestionsAttempted;
	}
	
	public boolean isUserRatingProvisional(){
		return this.noOfQuestionsAttempted <= (Integer)GameConstants.CONFIGURATION_MAP.get(GameConstants.PROVISIONAL_LIMIT_FOR_ELO_RATING_KEY);
	}
	
	public void incrementNoOfQuestionsAttempted(){
		this.noOfQuestionsAttempted++;
	}
}
