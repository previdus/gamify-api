package com.core.service.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.constants.EntityStateENUM;
import com.core.dao.ExamDAO;
import com.core.dao.ExamSectionDAO;
import com.core.dao.TopicDAO;
import com.core.domain.lms.Exam;
import com.core.domain.lms.ExamSection;
import com.core.domain.lms.Topic;
import com.core.service.ExamSectionService;
import com.core.service.ExamService;
import com.core.service.TopicService;
import com.core.stub.GameStub;

@Service("topicService")
public class TopicServiceImpl implements TopicService {

	@Autowired
	private TopicDAO topicDAO;
	
	@Autowired
	private ExamSectionService examSectionService;

	
	
    public List<Topic> showTopics(Long examSectionId){
    	return null;
    }
    
    public Topic saveTopic(Topic topic){
    	return topicDAO.saveOrUpdate(topic);
    }
    public Topic findById(Long topicId){
    	
    	return topicDAO.findObjectById(topicId);
    	
    }
	
    public Topic addTopic(Long examSectionId, String topicName){
    	Topic topic = new Topic();
    	topic.setName(topicName);
    	topic.setState(EntityStateENUM.INACTIVE.name());
    	ExamSection examSection = examSectionService.findById(examSectionId);    	
    	topic.setExamSection(examSection);    	
    	topic  = topicDAO.saveNew(topic);
    	return topic;
    }
	
	public Topic editTopic(Long topicId, String topicName)
	{
		Topic topic = this.findById(topicId);
		topic.setName(topicName);
		return this.saveTopic(topic);
		 
		
	}
	
	
	
	public List<Topic> findByNameAndExamSection(Long examSectionId, String name){
		return topicDAO.findTopicByNameForAnExamSection(examSectionId, name);
	}

}
