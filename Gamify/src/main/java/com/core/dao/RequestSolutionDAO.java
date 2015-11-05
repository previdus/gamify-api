package com.core.dao;

import java.io.Serializable;

import com.core.dao.generic.GenericRepository;
import com.core.domain.knockout.RequestSolution;

public interface RequestSolutionDAO extends
GenericRepository<RequestSolution, Serializable>{
	
	RequestSolution get(long gameId, long userId, long questionId);
	
	

}
