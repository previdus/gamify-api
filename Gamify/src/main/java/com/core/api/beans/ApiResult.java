package com.core.api.beans;

import com.core.domain.User;
import com.core.domain.knockout.GameInstance;

public class ApiResult{
	
	int status;
	String message;
	String redirectLink;
	String userToken;
	
	User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRedirectLink() {
		return redirectLink;
	}
	public void setRedirectLink(String redirectLink) {
		this.redirectLink = redirectLink;
	}
	public String getUserToken() {
		return userToken;
	}
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
	
	public void handleNotLoggedInUser(){
		 setStatus(-1);
     	 setMessage("User not logged in. Redirecting to the login page");
     	 //redirect to login page
     	 setRedirectLink("");
	}
	
}
