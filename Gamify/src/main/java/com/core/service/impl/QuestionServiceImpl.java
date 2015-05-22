package com.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.dao.QuestionDAO;
import com.core.domain.Question;
import com.core.domain.lms.Topic;
import com.core.service.QuestionService;


@Service("questionService")
public class QuestionServiceImpl implements QuestionService{
	
	@Autowired
	private QuestionDAO questionDAO;
	public List<Question> getQuestions(Topic topic){
		return questionDAO.getQuestions(topic);
	}
	public Question getQuestion(Topic topic) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Question> showQuestions(long topicId){
		return null;
	}
	public Question showQuestion(long topicId){
		return null;
	}
	public Question addQuestion(long topicId, int difficultyLevel, String imageUrl, String questionText){
		return null;
	}
	public Question editQuestion(long questionId, int difficultyLevel, String imageUrl, String questionText){
		return null;
	}
	public void deleteQuestion(long questionId){
		
	}

}
