package com.core.service;

import com.core.domain.User;

public interface UserService {
	
	public User getUser(String userName, String pwd);
	
	public User getUser(Long userId);
	
	public User getUserByName(String name);
	
	public User getUserByEmail(String email);
	
	public User getUserByParentsEmail(String parentsEmail);
	
	public User getUserByFacebookId(String facebookId);
	
	public User saveUser(User user);
	
	public boolean doesUserExist(String userName);
	
	public User getBautUser();

}
