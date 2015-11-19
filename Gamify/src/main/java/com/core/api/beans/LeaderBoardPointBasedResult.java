package com.core.api.beans;

import java.util.LinkedList;
import java.util.List;

public class LeaderBoardPointBasedResult extends ApiResult{
	
	private List<TotalNoOfPointsScoredByUser> totalNoOfPointsScoredByUsers = new LinkedList<TotalNoOfPointsScoredByUser>();
	private long topicId;
	private String startTime;
	private String endTime;
	
	public LeaderBoardPointBasedResult() {
	
	}
	
	public LeaderBoardPointBasedResult(
			List<TotalNoOfPointsScoredByUser> totalNoOfPointsScoredByUsers,
			long topicId, String startTime, String endTime) {
		super();
		this.totalNoOfPointsScoredByUsers = totalNoOfPointsScoredByUsers;
		this.topicId = topicId;
		this.startTime = startTime;
		this.endTime = endTime;
	}


	public List<TotalNoOfPointsScoredByUser> getTotalNoOfPointsScoredByUsers() {
		return totalNoOfPointsScoredByUsers;
	}

	public void setTotalNoOfPointsScoredByUsers(
			List<TotalNoOfPointsScoredByUser> totalNoOfPointsScoredByUsers) {
		this.totalNoOfPointsScoredByUsers = totalNoOfPointsScoredByUsers;
	}

	public long getTopicId() {
		return topicId;
	}

	public void setTopicId(long topicId) {
		this.topicId = topicId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
