package com.core.dao;

import java.io.Serializable;
import java.util.List;

import com.core.dao.generic.GenericRepository;
import com.core.domain.Question;
import com.core.domain.lms.Topic;

public interface QuestionDAO extends GenericRepository<Question, Serializable> {

	public List<Question> getQuestions(Topic topic);

	public Question getQuestion(Topic topic);
	
	public  List<Question> findByQuestionTextAndTopic(long topicId, String questionText);

}
