package com.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.api.beans.TotalNumberOfGameWonByAUser;
import com.core.dao.GameInstanceDAO;
import com.core.service.LeaderBoardService;

@Service("leaderBoardService")
public class LeaderBoardServiceImpl implements LeaderBoardService{
	
	@Autowired
	GameInstanceDAO gameInstanceDAO;
	
	public List<TotalNumberOfGameWonByAUser> getTopPersonWhoWonMaxGames(Integer numOfTopPlayersToShow){
		return gameInstanceDAO.getTopPersonWhoWonMaxGames(numOfTopPlayersToShow);
	}

}
