package com.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.core.service.UserPointsService;

@Service("userPointsService")
public class UserPointsServiceImpl implements UserPointsService {
	
	private static final Logger log = LoggerFactory.getLogger(UserPointsServiceImpl.class);

	public void addPoints(long userId, int points) {
		
		
	}

}
