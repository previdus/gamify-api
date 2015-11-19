package com.core.service;

import java.util.List;

import com.core.api.beans.TotalNoOfPointsScoredByUser;
import com.core.api.beans.TotalNumberOfGameWonByAUser;

public interface LeaderBoardService {

	
	public List<TotalNumberOfGameWonByAUser> getTopPersonWhoWonMaxGames(Integer numOfTopPlayersToShow);
	
	public List<TotalNoOfPointsScoredByUser> getTopPersonsWhoScoredMaxPointsInLMS(long topicId);
}
