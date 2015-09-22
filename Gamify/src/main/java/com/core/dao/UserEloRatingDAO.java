package com.core.dao;

import java.io.Serializable;
import java.util.List;

import com.core.dao.generic.GenericRepository;
import com.core.domain.knockout.UserEloRating;

public interface UserEloRatingDAO extends GenericRepository<UserEloRating, Serializable> {
	
	public UserEloRating getUserEloRatingByUserId(Long userId);
	
	public List<UserEloRating> getTopUserEloRatings(Integer noOfPlayersToShow, boolean excludeProvisional);

}
