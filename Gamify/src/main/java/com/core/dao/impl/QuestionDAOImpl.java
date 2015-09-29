package com.core.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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

	public List<Question> getQuestions(Topic topic) {
		log.info("getting questions for topic");
		Session session = this.getSession();
		Query qry = session.createQuery(
				"from Question where topic = :selectedtopic order by questionFrequency asc").setParameter(
				"selectedtopic", topic);
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
