package com.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.constants.EntityStateENUM;
import com.core.dao.QuestionDAO;
import com.core.domain.Question;
import com.core.domain.lms.Topic;
import com.core.service.QuestionService;
import com.core.service.TopicService;

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionDAO questionDAO;
	
	@Autowired
	private TopicService topicService;

	public List<Question> getQuestions(Topic topic) {
		return questionDAO.getQuestions(topic);
	}

	public Question saveQuestion(Question question){
		return questionDAO.saveOrUpdate(question);
	}
	
	public Question findById(long questionId){
		return questionDAO.findObjectById(questionId);
	}
	
	
	public Question addQuestion(long topicId, byte difficultyLevel, String imageUrl, String questionText){
		Question question = new Question();
		Topic topic = topicService.findById(topicId);
		question.setTopic(topic);
		question.setImageUrl(imageUrl);
		question.setState(EntityStateENUM.INACTIVE.name());
		question.setDifficultyLevel(difficultyLevel);
		question.setQuestionText(questionText);
		return questionDAO.saveOrUpdate(question);
		
	}
	public Question editQuestionText(long questionId, String questionText){
		Question question = findById(questionId);	
		question.setQuestionText(questionText);
		return questionDAO.saveOrUpdate(question);
		
	}
	public Question updateQuestionImage(long questionId, String imageUrl){
		Question question = findById(questionId);	
		question.setImageUrl(imageUrl);
		return questionDAO.saveOrUpdate(question);
		
	}
	public Question editQuestionDifficultyLevel(long questionId, byte difficultyLevel){
		Question question = findById(questionId);	
		question.setDifficultyLevel(difficultyLevel);
		return questionDAO.saveOrUpdate(question);
		
	}
	public List<Question> findByTextAndTopic(long topicId, String questionText){		
		return questionDAO.findByQuestionTextAndTopic(topicId, questionText);
	}
	

}
