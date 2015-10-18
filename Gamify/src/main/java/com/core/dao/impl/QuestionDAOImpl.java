package com.core.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.core.constants.EntityStateENUM;
import com.core.dao.AnswerKeyDAO;
import com.core.dao.QuestionDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.Question;
import com.core.domain.lms.Topic;

@Repository("questionDAO")
public class QuestionDAOImpl extends
		HibernateGenericRepository<Question, Serializable> implements
		QuestionDAO {

	private static final Logger log = LoggerFactory
			.getLogger(QuestionDAOImpl.class);
	
	

	private List<Question> getQuestions(Topic topic, String state) {
		log.info("getting questions for topic");
		Session session = this.getSession();
		Query qry = session.createQuery(
				"select quest from Question quest, AnswerKey answerKey  where quest.topic = :selectedtopic "
				+ "and quest.id = answerKey.questionId and quest.maxTimeToAnswerInSeconds > 0 "
				+ ((state != null)?"and quest.state = :state ":"")
				+ "order by quest.questionFrequency asc")
				.setParameter("selectedtopic", topic);
		if(state != null){
			qry.setParameter("state", state);
		}
				
		log.info("before");
		List<Question> questions = qry.list();
		log.info("got questions for topic");
		releaseSession(session);
		if (questions != null && questions.size() > 0) {
			log.info(" questions.size() > 0 for topic");
			return questions;

		}
		log.info(" questions.size() == 0 for topic");
		return null;
	}
	
	public List<Question> getEnabledQuestions(Topic topic) {
		return this.getQuestions(topic, EntityStateENUM.ACTIVE.toString());
	}
	
	public List<Question> getDisabledQuestions(Topic topic) {
		return this.getQuestions(topic, EntityStateENUM.INACTIVE.toString());
	}
	
	public List<Question> getAllQuestions(Topic topic) {
		return this.getQuestions(topic, null);
	}
	

	public Question getQuestion(Topic topic) {
		log.info("getting questions for topic");
		Session session = this.getSession();
		Query qry = session
				.createQuery(
						"from Question where topic = :selectedtopic order by rand() ")
				.setParameter("selectedtopic", topic).setFetchSize(1);
		log.info("before");
		List<Question> questions = qry.list();
		log.info("got questions for topic");
		releaseSession(session);
		if (questions != null && questions.size() > 0) {
			log.info(" questions.size() > 0 for topic");
			return questions.get(0);

		}
		log.info(" questions.size() == 0 for topic");
		return null;
	}
	
	public  List<Question> findByQuestionTextAndTopic(long topicId, String questionText){
		Map<String, Object> keyValueMap = new HashMap<String, Object>();
		keyValueMap.put("questionText", questionText);
		keyValueMap.put("topic.id", topicId);
        return findObjectsByKeyMap(Question.class,keyValueMap);
    }

}
