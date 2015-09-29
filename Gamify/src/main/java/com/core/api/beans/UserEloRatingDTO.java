package com.core.api.beans;

import java.io.Serializable;

public class UserEloRatingDTO implements Serializable {

	private Long userId;
	private String displayName;
	private Integer eloRating;
	private Integer numberOfQuestionsAttempted;
	private Integer numberOfTopicsCovered;
	private Integer numberOfExamSectionsCovered;
	
	private String ratedContext;
	
	public String getRatedContext() {
		return ratedContext;
	}
	public void setRatedContext(String ratedContext) {
		this.ratedContext = ratedContext;
	}
	public Integer getNumberOfTopicsCovered() {
		return numberOfTopicsCovered;
	}
	public void setNumberOfTopicsCovered(Integer numberOfTopicsCovered) {
		this.numberOfTopicsCovered = numberOfTopicsCovered;
	}
	public Integer getNumberOfExamSectionsCovered() {
		return numberOfExamSectionsCovered;
	}
	public void setNumberOfExamSectionsCovered(Integer numberOfExamSectionsCovered) {
		this.numberOfExamSectionsCovered = numberOfExamSectionsCovered;
	}
	private String imageUrl;
	private Double percentile;
	private String percentileDisplay;
	public String getPercentileDisplay() {
		if(percentile%10 == 1)
		{
			percentileDisplay = percentile.intValue()+"st";
		}
		else if(percentile%10 == 2){
			percentileDisplay = percentile.intValue()+"nd";
		}
		else if(percentile%10 == 2){
			percentileDisplay = percentile.intValue()+"rd";
		}
		else {
			percentileDisplay = percentile.intValue()+"th";
		}
		
		return percentileDisplay;
	}
	
	public Double getPercentile() {
		return percentile;
	}
	public void setPercentile(Double percentile) {
		this.percentile = percentile;
	}
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
