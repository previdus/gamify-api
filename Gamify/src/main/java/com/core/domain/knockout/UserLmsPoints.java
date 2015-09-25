package com.core.domain.knockout;

public class UserLmsPoints {
	
	long userId;
	int points;
	
	
	public UserLmsPoints(long userId, int points) {
		super();
		this.userId = userId;
		this.points = points;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	
	

}
