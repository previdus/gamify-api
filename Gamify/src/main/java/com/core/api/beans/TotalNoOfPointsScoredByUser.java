package com.core.api.beans;

public class TotalNoOfPointsScoredByUser {
	
	String userName;
	String userImageUrl;
	long points;
	
	
	public TotalNoOfPointsScoredByUser(String userName, String userImageUrl,
			long points) {
		super();
		this.userName = userName;
		this.userImageUrl = userImageUrl;
		this.points = points;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserImageUrl() {
		return userImageUrl;
	}


	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}


	public long getPoints() {
		return points;
	}


	public void setPoints(long points) {
		this.points = points;
	} 
	
	
	
	
	

}
