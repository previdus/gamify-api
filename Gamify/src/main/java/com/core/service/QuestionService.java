package com.core.service;

import java.util.List;

import com.core.domain.Question;
import com.core.domain.lms.Topic;



public interface QuestionService {
	public List<Question> getQuestions(Topic topic);
	public Question getQuestion(Topic topic);
	public List<Question> showQuestions(long topicId);
	public Question showQuestion(long topicId);
	public Question addQuestion(long topicId, int difficultyLevel, String imageUrl, String questionText);
	public Question editQuestion(long questionId, int difficultyLevel, String imageUrl, String questionText);
	public void deleteQuestion(long questionId);
}
