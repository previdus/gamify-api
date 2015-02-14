package com.core.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.core.dao.AnswerKeyDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.AnswerKey;
import com.core.domain.Option;
import com.core.domain.Question;
import com.core.service.impl.AnswerKeyServiceImpl;
import com.core.stub.GameStub;

@Repository("answerKeyDAO")
public class AnswerKeyDAOImpl extends HibernateGenericRepository<AnswerKey, Serializable> implements AnswerKeyDAO{
	
	private static final Logger log = LoggerFactory.getLogger(AnswerKeyDAOImpl.class);

	
	
	//@Transactional(readOnly=true)
	public boolean isCorrectAnswer(Long questionId,Option answer){
		//Option option = GameStub.questionCorrectAnswerMap.get(questionId);
		Long correctAns =  getAnswerKey(new Question(questionId)).getOptionId();
		log.info("Question Id " + questionId + " Correct Answer " +  getAnswerKey(new Question(questionId)).getOptionId() + "Answered " + answer.getId());
		return correctAns.equals(answer.getId());
	}
	
	//@Transactional(readOnly=true)
	public AnswerKey getAnswerKey(Question question){
		try{
		log.info("Getting Answer Key ***********************************************************************************************");
		Query qry = getSession().createQuery("from AnswerKey where questionId = :quesId").setParameter("quesId", question.getId());
		List<AnswerKey> answers =  qry.list();
		return answers.get(0);
		}catch(Exception ex){
			return null;
		}
		/*Option option =  GameStub.questionCorrectAnswerMap.get(question.getId());
		AnswerKey answerKey = new AnswerKey();
		answerKey.setAnswer(option);
		answerKey.setQuestion(question);
		return answerKey;*/
	}
}
