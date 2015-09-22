package com.core.api.beans;

import java.io.Serializable;

public class UserEloRatingDTO implements Serializable {

	private Long userId;
	private String displayName;
	private Integer eloRating;
	private Integer numberOfQuestionsAttempted;
	private String imageUrl;
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Integer getEloRating() {
		return eloRating;
	}
	public void setEloRating(Integer eloRating) {
		this.eloRating = eloRating;
	}
	public Integer getNumberOfQuestionsAttempted() {
		return numberOfQuestionsAttempted;
	}
	public void setNumberOfQuestionsAttempted(Integer numberOfQuestionsAttempted) {
		this.numberOfQuestionsAttempted = numberOfQuestionsAttempted;
	}
	
	
}
