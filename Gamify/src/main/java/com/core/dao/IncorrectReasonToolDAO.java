package com.core.dao;

import java.io.Serializable;

import com.core.dao.generic.GenericRepository;
import com.core.domain.knockout.IncorrectReasonTool;

public interface IncorrectReasonToolDAO extends
GenericRepository<IncorrectReasonTool, Serializable>{
	
	public IncorrectReasonTool get(long gameId, long userId, long questionId);
	
	

}
