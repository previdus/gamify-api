package com.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.dao.AnswerKeyDAO;
import com.core.domain.AnswerKey;
import com.core.domain.Option;
import com.core.domain.Question;
import com.core.service.AnswerKeyService;
import com.core.service.OptionService;
import com.core.service.QuestionService;

@Service("answerKeyService")
public class AnswerKeyServiceImpl implements AnswerKeyService {

	private static final Logger log = LoggerFactory
			.getLogger(AnswerKeyServiceImpl.class);

	@Autowired
	private AnswerKeyDAO answerKeyDAO;// = new AnswerKeyDAOImpl();
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private OptionService optionService;

	public boolean isCorrectAnswer(Long questionId, Option answer) {
		if (answer == null)
			return false;
		log.info("answerKeyService questionId:" + questionId + ", optionId:"
				+ answer.getId());
		AnswerKey key = getAnswerKey(new Question(questionId));
		if (key != null && key.getOptionId().equals(answer.getId()))
			return true;
		else
			return false;
		// return answerKeyDAO.isCorrectAnswer(questionId, answer);
	}

	public AnswerKey getAnswerKey(Question question) {
		return answerKeyDAO.getAnswerKey(question);
	}
	
	public AnswerKey saveAnswerKey(Long questionId, Long optionId){
		Question question = questionService.findById(questionId);
	     Option option = optionService.findById(optionId);
	     AnswerKey answerKey = new AnswerKey();
	     answerKey.setQuestion(question);
	     answerKey.setAnswer(option);
	     return answerKeyDAO.saveOrUpdate(answerKey);
	     
	}

}
