package com.core.dao;

import java.io.Serializable;
import java.util.List;

import com.core.dao.generic.GenericRepository;
import com.core.domain.lms.Topic;

public interface TopicDAO extends GenericRepository<Topic, Serializable> {
	
	public List<Topic> findTopicByNameForAnExamSection(Long examSectionId, String name);

}
