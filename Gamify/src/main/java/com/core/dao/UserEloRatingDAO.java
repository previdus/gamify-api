package com.core.dao;

import java.io.Serializable;

import com.core.dao.generic.GenericRepository;
import com.core.domain.knockout.UserEloRating;

public interface UserEloRatingDAO extends GenericRepository<UserEloRating, Serializable> {
	
	public UserEloRating getUserEloRatingByUserId(Long userId);

}
