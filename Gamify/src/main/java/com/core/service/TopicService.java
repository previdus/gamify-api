package com.core.service;

import java.util.List;

import com.core.domain.lms.Topic;

public interface TopicService {
	

	
	
	public Topic addTopic(Long examSectionId, String topicName);
	
	public Topic editTopic(Long topicId, String topicName);	
	
	public List<Topic> findByNameAndExamSection(Long examSectionId, String topicName);	
	
	public Topic findById(Long topicId);
	
	public Topic saveTopic(Topic topic);
	
}
