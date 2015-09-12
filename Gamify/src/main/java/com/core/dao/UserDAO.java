package com.core.dao;

import java.io.Serializable;

import com.core.dao.generic.GenericRepository;
import com.core.domain.User;

public interface UserDAO extends GenericRepository<User, Serializable> {
	public User getUser(String userName, String pwd);

	public User getUser(Long userId);

	public User getUserByName(String userName);

	public User getUserByEmail(String email);

	public User getUserByFacebookId(String facebookId);
	
	public boolean doesUserExist(String userName);
	
}
