package com.core.dao;

import java.io.Serializable;
import java.util.List;

import com.core.api.beans.TotalNoOfPointsScoredByUser;
import com.core.dao.generic.GenericRepository;
import com.core.domain.knockout.UserPoints;

public interface UserPointsDAO extends GenericRepository<UserPoints, Serializable>{

	public List<TotalNoOfPointsScoredByUser> getTopPersonsWhoScoredMaxPointsInLMS(long topicId);

}
