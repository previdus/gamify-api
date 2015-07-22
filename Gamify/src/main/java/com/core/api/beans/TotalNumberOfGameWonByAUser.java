package com.core.api.beans;

import java.io.Serializable;

import com.core.domain.User;

public class TotalNumberOfGameWonByAUser implements Serializable {
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Long getTotalNoOfWins() {
		return totalNoOfWins;
	}


	public void setTotalNoOfWins(Long totalNoOfWins) {
		this.totalNoOfWins = totalNoOfWins;
	}


	public static final long serialVersionUID = 983939238378393L;

	private  User user;
	private Long  totalNoOfWins;
	
	
	public TotalNumberOfGameWonByAUser(User user, Long totalNoOfWins) {
		this.user = user;
		this.totalNoOfWins = totalNoOfWins;
	}
	
}
