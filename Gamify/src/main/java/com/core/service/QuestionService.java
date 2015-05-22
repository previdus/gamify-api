package com.core.service;

import java.util.List;

import com.core.domain.Question;
import com.core.domain.lms.Topic;

public interface QuestionService {
	public List<Question> getQuestions(Topic topic);

	public Question getQuestion(Topic topic);
}
