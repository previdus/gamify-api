package com.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.dao.UserDAO;
import com.core.dao.UserPointsDAO;
import com.core.domain.User;
import com.core.domain.knockout.UserPoints;
import com.core.exception.ConstraintException;
import com.core.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	private static final Logger log = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	public UserDAO userDAO;
	
	@Autowired
	public UserPointsDAO userPointsDAO;

	public User getUser(String userName, String pwd) {
		return userDAO.getUser(userName, pwd);
	}
	
	public boolean doesUserExist(String userName){
		return userDAO.doesUserExist(userName);
	}

	public User getUser(Long userId) {
		return userDAO.findObjectByIdImmediate(userId);
	}

	public User getUserByName(String name) {
		return userDAO.getUserByName(name);
	}

	public User getUserByEmail(String email) {
		return userDAO.getUserByEmail(email);
	}
	
	public User getUserByParentsEmail(String parentsEmail){
		return userDAO.getUserByParentsEmail(parentsEmail);
	}

	public User getUserByFacebookId(String facebookId) {
		return userDAO.getUserByFacebookId(facebookId);
	}

	public User saveUser(User user) {
		try {
			
			return userDAO.saveOrUpdate(user);
		} catch (ConstraintException ce) {
			log.info(ce.getMessage());
			throw ce;
		}

	}
	
	public User getBotUser() {
				return userDAO.getBotUser();
			}

	public void addLmsPoints(Long userId, int points,long topicId) {
		userDAO.addLmsPoints(userId, points);
		userPointsDAO.saveNew(new UserPoints(new User(userId), points, topicId));
		
	}

}
