package com.core.api.beans;

import com.core.domain.User;

public class TotalNumberOfGameWonByAUser {

	User user;
	Long  totalNoOfWins;
	
	
	public TotalNumberOfGameWonByAUser(User user, Long totalNoOfWins) {
		this.user = user;
		this.totalNoOfWins = totalNoOfWins;
	}
	
}
