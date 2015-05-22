package com.core.service;

import java.util.List;

import com.core.domain.Question;
import com.core.domain.lms.Exam;
import com.core.domain.lms.ExamSection;
import com.core.domain.lms.Topic;

public interface TopicService {
	

	public List<Topic> showTopics(Long examSectionId);
	
	public Topic addTopic(Long examSectionId, String topicName);
	
	public Topic editTopic(Long topicId, String topicName);
	
	public void detachTopicFromExamSection(Long examSectionId,Long topicId);
	
	public void attachTopicToExamSection(Long examSectionId, Long topicId);
	
	public void deleteTopic(Long topicId);
	
}
