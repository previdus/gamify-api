package com.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.dao.UserPointsDAO;
import com.core.service.UserPointsService;

@Service("userPointsService")
public class UserPointsServiceImpl implements UserPointsService {
	
	private static final Logger log = LoggerFactory.getLogger(UserPointsServiceImpl.class);
	
	@Autowired
	private UserPointsDAO userPointsDAO;

	public void addPoints(long userId, int points) {
		userPointsDAO.addPoints(userId, points);
	}

}
