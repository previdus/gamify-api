package com.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.api.beans.TotalNoOfPointsScoredByUser;
import com.core.api.beans.TotalNumberOfGameWonByAUser;
import com.core.dao.GameInstanceDAO;
import com.core.dao.UserPointsDAO;
import com.core.service.LeaderBoardService;

@Service("leaderBoardService")
public class LeaderBoardServiceImpl implements LeaderBoardService{
	
	@Autowired
	GameInstanceDAO gameInstanceDAO;
	
	@Autowired
	UserPointsDAO userPointsDAO;
	
	public List<TotalNumberOfGameWonByAUser> getTopPersonWhoWonMaxGames(Integer numOfTopPlayersToShow){
		return gameInstanceDAO.getTopPersonWhoWonMaxGames(numOfTopPlayersToShow);
	}

	public List<TotalNoOfPointsScoredByUser> getTopPersonsWhoScoredMaxPointsInLMS(
			long topicId) {
		List<TotalNoOfPointsScoredByUser> topScorers = userPointsDAO.getTopPersonsWhoScoredMaxPointsInLMS(topicId);
		return topScorers;
	}

}
