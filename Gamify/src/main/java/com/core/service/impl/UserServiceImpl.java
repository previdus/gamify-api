package com.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.controller.LoginController;
import com.core.dao.UserDAO;
import com.core.domain.User;
import com.core.exception.ConstraintException;
import com.core.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	public UserDAO userDAO;

	public User getUser(String userName, String pwd) {
		return userDAO.getUser(userName, pwd);
	}
	
	public boolean doesUserExist(String userName){
		return userDAO.doesUserExist(userName);
	}

	public User getUser(Long userId) {
		return userDAO.getUser(userId);
	}

	public User getUserByName(String name) {
		return userDAO.getUserByName(name);
	}

	public User getUserByEmail(String email) {
		return userDAO.getUserByEmail(email);
	}

	public User getUserByFacebookId(String facebookId) {
		return userDAO.getUserByFacebookId(facebookId);
	}

	public User saveUser(User user) {
		try {
			
			return userDAO.saveOrUpdate(user);
		} catch (ConstraintException ce) {
			log.info(ce.getMessage());
			return null;
		}

	}

	public User getBautUser() {
		return userDAO.getBautUser();
		 
	}

}
