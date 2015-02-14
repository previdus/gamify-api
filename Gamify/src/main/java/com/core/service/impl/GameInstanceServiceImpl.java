package com.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.dao.GameInstanceDAO;
import com.core.domain.knockout.GameInstance;
import com.core.service.GameInstanceService;

@Service("gameInstanceService")
public class GameInstanceServiceImpl implements GameInstanceService {

	@Autowired
	GameInstanceDAO gameInstanceDAO;

	
	public GameInstance saveOrUpdate(GameInstance gi) {
		return gameInstanceDAO.saveOrUpdate(gi);
	}
	
	
	
	

}
