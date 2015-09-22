package com.core.service;

import java.util.List;

import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.UserEloRating;

public interface UserEloRatingService {

	public void calulateUserEloRating(GameInstance gameInstance);
	public List<UserEloRating> getTopUserEloRatings(Integer noOfPlayersToShow, boolean excludeProvisional);
	
	

}
