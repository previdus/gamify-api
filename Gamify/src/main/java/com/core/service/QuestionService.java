package com.core.service;

import java.util.List;

import com.core.constants.EntityStateENUM;
import com.core.constants.QuestionTypeENUM;
import com.core.domain.Option;
import com.core.domain.Question;
import com.core.domain.lms.Topic;

public interface QuestionService {
	public List<Question> getEnabledQuestions(Topic topic);
	public List<Question> getDisabledQuestions(Topic topic);
	public List<Question> getAllQuestions(Topic topic);
	public Question findById(long questionId);
	public Question saveQuestion(Question question);
	public void saveMCQQuestion(Question question, List<Option> incorrectOptions, Option correctOption);
	public void saveFreeTextQuestion(Question question, Option correctOption);
	public Question addQuestion(long topicId, byte difficultyLevel, String imageUrl, String questionText);
	public Question editQuestionText(long questionId, String questionText);
	public Question updateQuestionImage(long questionId, String imageUrl);
	public Question editQuestionDifficultyLevel(long questionId, byte difficultyLevel);	
	public List<Question> findByTextAndTopic(long topicId, String questionText);
	public Question updateQuestionText(long questionId, String questionText);
	public Question updateQuestionTimeAllocation(long questionId,Integer timeAllocated);
	public Question enableQuestion(long questionId);
	public Question disableQuestion(long questionId);
	public Question softDeleteQuestion(long questionId);
	public Option addNewOption(Long questionId, String optionText);
	public List<Question> findByTopicStatePageNo(long topicId, EntityStateENUM state,
			int pageNo, int limit);
	public Question editQuestionType(Long questionId, QuestionTypeENUM valueOf);
	public Question editQuestionPostText(Long questionId,
			String updatePostTextForFreeTextQuestion);
	public Question editQuestionPreText(Long questionId,
			String updatePreTextForFreeTextQuestion);

	
}
