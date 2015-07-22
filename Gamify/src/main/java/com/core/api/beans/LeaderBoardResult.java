package com.core.api.beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class LeaderBoardResult implements Serializable{
	
	
	private static final long serialVersionUID = -2810565975850900117L;
	
	
	List<TotalNumberOfGameWonByAUser> topUsers = new LinkedList<TotalNumberOfGameWonByAUser>();
	
	public LeaderBoardResult() {
	}
	
	public LeaderBoardResult(List<TotalNumberOfGameWonByAUser> topUsers) {
		this.topUsers = topUsers;
	}

	public List<TotalNumberOfGameWonByAUser> getTopUsers() {
		return topUsers;
	}

	public void setTopUsers(List<TotalNumberOfGameWonByAUser> topUsers) {
		this.topUsers = topUsers;
	}
	
	
	
	

}
