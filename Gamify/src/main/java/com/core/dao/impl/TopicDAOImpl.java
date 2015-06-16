package com.core.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.core.dao.TopicDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.lms.ExamSection;
import com.core.domain.lms.Topic;

@Repository("topicDAO")
public class TopicDAOImpl extends
		HibernateGenericRepository<Topic, Serializable> implements TopicDAO {
	
	public List<Topic> findTopicByNameForAnExamSection(Long examSectionId, String name){
		Map<String, Object> keyValueMap = new HashMap<String, Object>();
		keyValueMap.put("name", name);
		keyValueMap.put("examSection.id", examSectionId);
        return findObjectsByKeyMap(Topic.class,keyValueMap);
    }

}
