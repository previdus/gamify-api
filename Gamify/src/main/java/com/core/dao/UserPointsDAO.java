package com.core.dao;

import java.io.Serializable;
import com.core.dao.generic.GenericRepository;
import com.core.domain.UserPoints;

public interface UserPointsDAO extends GenericRepository<UserPoints, Serializable> {

	
	public void addPoints(long userId, int points);
}
