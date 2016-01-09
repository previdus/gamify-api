package com.core.dao;

import java.io.Serializable;

import com.core.api.beans.StudentStats;
import com.core.dao.generic.GenericRepository;
import com.core.domain.knockout.PlayerResponseLog;

public interface PlayerResponseLogDAO extends GenericRepository<PlayerResponseLog, Serializable>{
	
	public StudentStats getQuestionStats(long userId, long topicId);
	
	public StudentStats getQuestionAverageStats(long topicId);
	
	

}
