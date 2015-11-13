package com.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.core.constants.EntityStateENUM;
import com.core.constants.QuestionTypeENUM;
import com.core.dao.AnswerKeyDAO;
import com.core.dao.OptionDAO;
import com.core.dao.QuestionDAO;
import com.core.domain.AnswerKey;
import com.core.domain.Option;
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
	
	@Autowired 
	private AnswerKeyDAO answerKeyDAO;
	
	@Autowired
	private OptionDAO optionDAO;

	public List<Question> getEnabledQuestions(Topic topic) {
		return questionDAO.getEnabledQuestions(topic);
	}
	
	public List<Question> getDisabledQuestions(Topic topic) {
		return questionDAO.getDisabledQuestions(topic);
	}
	
	public List<Question> getAllQuestions(Topic topic) {
		return questionDAO.getAllQuestions(topic);
	}

	public Question saveQuestion(Question question){
		return questionDAO.saveOrUpdate(question);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public void saveMCQQuestion(Question question, List<Option> incorrectOptions, Option correctOption){
		question =  questionDAO.saveNew(question);
		for(Option incorrectOption: incorrectOptions){
			incorrectOption.setQuestion(question);
			optionDAO.saveNew(incorrectOption);
		}
		correctOption.setQuestion(question);
		correctOption = optionDAO.saveNew(correctOption);
		answerKeyDAO.saveOrUpdate(new AnswerKey(question.getId(),correctOption.getId()));
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public void saveFreeTextQuestion(Question question,
			Option correctOption) {
		question =  questionDAO.saveOrUpdate(question);
		correctOption.setQuestion(question);
		correctOption = optionDAO.saveNew(correctOption);
		answerKeyDAO.saveOrUpdate(new AnswerKey(question.getId(),correctOption.getId()));
	}
	
	public Question findById(long questionId){
		return questionDAO.findObjectById(questionId);
	}
	
	
	public Question addQuestion(long topicId, byte difficultyLevel, String imageUrl, String questionText){
		Question question = new Question();
		Topic topic = topicService.findById(topicId);
		question.setTopic(topic);
		question.setImageUrl(imageUrl);
		question.setState(EntityStateENUM.INACTIVE);
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

	public Question updateQuestionText(long questionId, String questionText) {
		Question question = questionDAO.findObjectByIdImmediate(questionId);
		question.setQuestionText(questionText);
		return questionDAO.saveOrUpdate(question);
	}

	public Question updateQuestionTimeAllocation(long questionId,
			Integer timeAllocated) {
		Question question = questionDAO.findObjectByIdImmediate(questionId);
		question.setMaxTimeToAnswerInSeconds(timeAllocated);
		return questionDAO.saveOrUpdate(question);
	}
	
	public Question enableQuestion(long questionId){
		Question question = questionDAO.findObjectByIdImmediate(questionId);
		question.setState(EntityStateENUM.ACTIVE);
		return questionDAO.saveOrUpdate(question);
	}
	
	public Question disableQuestion(long questionId){
		Question question = questionDAO.findObjectByIdImmediate(questionId);
		question.setState(EntityStateENUM.INACTIVE);
		return questionDAO.saveOrUpdate(question);
	}
	
	public Question softDeleteQuestion(long questionId){
		Question question = questionDAO.findObjectByIdImmediate(questionId);
		question.setState(EntityStateENUM.SOFT_DELETE);
		return questionDAO.saveOrUpdate(question);
	}

	public Option addNewOption(Long questionId, String optionText) {
		Question question = questionDAO.findObjectByIdImmediate(questionId);
		int order = question.getOptions() == null ? 1: question.getOptions().size() + 1;
		Option option = new Option(optionText, "", order, question, EntityStateENUM.REVIEW_PENDING);
		return optionDAO.saveNew(option);
	}

	public List<Question> findByTopicStatePageNo(long topicId, EntityStateENUM state,
			int pageNo, int limit) {
		return questionDAO.findByTopicStatePageNo(topicId,state,pageNo, limit);
	}

	public Question editQuestionType(Long questionId, QuestionTypeENUM questionType) {
		Question question = questionDAO.findObjectByIdImmediate(questionId);
		question.setQuestionType(questionType);
		return questionDAO.saveOrUpdate(question);
		
	}

	public Question editQuestionPostText(Long questionId,
			String updatePostTextForFreeTextQuestion) {
		Question question = questionDAO.findObjectByIdImmediate(questionId);
		question.setPostTextForFreeTextQustion(updatePostTextForFreeTextQuestion);
		return questionDAO.saveOrUpdate(question);
		
	}

	public Question editQuestionPreText(Long questionId,
			String updatePreTextForFreeTextQuestion) {
		Question question = questionDAO.findObjectByIdImmediate(questionId);
		question.setPreTextForFreeTextQustion(updatePreTextForFreeTextQuestion);
		return questionDAO.saveOrUpdate(question);
		
	}

	
	

}
