package com.core.dao;

import java.io.Serializable;
import java.util.List;

import com.core.api.beans.TotalNumberOfGameWonByAUser;
import com.core.dao.generic.GenericRepository;
import com.core.domain.knockout.GameInstance;

public interface GameInstanceDAO extends
		GenericRepository<GameInstance, Serializable> {
	
	public List<TotalNumberOfGameWonByAUser> getTopFivePersonWhoWonMaxGames();

}
