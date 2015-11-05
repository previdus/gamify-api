package com.core.dao;

import java.io.Serializable;

import com.core.dao.generic.GenericRepository;
import com.core.domain.knockout.StarMarkQuestion;

public interface StarMarkQuestionDAO extends
GenericRepository<StarMarkQuestion, Serializable>{
	
	 StarMarkQuestion get(long gameId, long userId, long questionId);
	
	

}
