package com.core.service;

import java.util.List;

import com.core.domain.Question;
import com.core.domain.lms.Topic;

public interface QuestionService {
	public List<Question> getEnabledQuestions(Topic topic);
	public List<Question> getDisabledQuestions(Topic topic);
	public List<Question> getAllQuestions(Topic topic);
	public Question findById(long questionId);
	public Question saveQuestion(Question question);

	public Question addQuestion(long topicId, byte difficultyLevel, String imageUrl, String questionText);
	public Question editQuestionText(long questionId, String questionText);
	public Question updateQuestionImage(long questionId, String imageUrl);
	public Question editQuestionDifficultyLevel(long questionId, byte difficultyLevel);	
	public List<Question> findByTextAndTopic(long topicId, String questionText);

	
}
