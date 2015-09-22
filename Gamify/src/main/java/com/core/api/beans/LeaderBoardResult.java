package com.core.api.beans;

import java.util.LinkedList;
import java.util.List;

public class LeaderBoardResult extends ApiResult{
	
	
	//private static final long serialVersionUID = -2810565975850900117L;
	
	
	private List<TotalNumberOfGameWonByAUser> topUsers = new LinkedList<TotalNumberOfGameWonByAUser>();
	private List<UserEloRatingDTO> topRatedUsers = new LinkedList<UserEloRatingDTO>();
	

	public List<UserEloRatingDTO> getTopRatedUsers() {
		return topRatedUsers;
	}

	public void setTopRatedUsers(List<UserEloRatingDTO> topRatedUsers) {
		this.topRatedUsers = topRatedUsers;
	}

	public List<TotalNumberOfGameWonByAUser> getTopUsers() {
		return topUsers;
	}

	public void setTopUsers(List<TotalNumberOfGameWonByAUser> topUsers) {
		this.topUsers = topUsers;
	}
	
	
	
	

}
