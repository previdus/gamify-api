package com.core.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.core.constants.EntityStateENUM;
import com.core.dao.OptionDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.Option;
import com.core.domain.Question;
import com.core.domain.lms.Topic;

@Repository("optionDAO")
public class OptionDAOImpl extends
		HibernateGenericRepository<Option, Serializable> implements OptionDAO {
	
	private static final Logger log = LoggerFactory
			.getLogger(QuestionDAOImpl.class);
	
	public List<Option> getOptions(Question	question) {
		log.info("getting options for question");
		Query qry = getSession().createQuery(
				"from Option where question = :selectedquestion order by id desc").setParameter(
				"selectedquestion", question);
		log.info("before");
		List<Option> options = qry.list();
		log.info("got options for question");
		if (options != null && options.size() > 0) {
			log.info(" options.size() > 0 for question");
			return options;

		}
		log.info(" options.size() == 0 for question");
		return null;
	}

	public Option getOption(Question question) {
		log.info("getting options for question");
		Query qry = getSession()
				.createQuery(
						"from Option where question = :selectedquestion order by rand() ")
				.setParameter("selectedquestion", question).setFetchSize(1);
		log.info("before");
		List<Option> options = qry.list();
		log.info("got options for question");
		if (options != null && options.size() > 0) {
			log.info(" options.size() > 0 for question");
			return options.get(0);

		}
		log.info(" options.size() == 0 for question");
		return null;
	}
	
	public  List<Option> findByOptionTextAndQuestion(long questionId, String optionText){
		Map<String, Object> keyValueMap = new HashMap<String, Object>();
		keyValueMap.put("text", optionText);
		keyValueMap.put("question.id", questionId);
        return findObjectsByKeyMap(Option.class,keyValueMap);
	}
	
	public  List<Option> findActiveOptionByOrderAndQuestion(long questionId, int order){
		Map<String, Object> keyValueMap = new HashMap<String, Object>();
		keyValueMap.put("ordr", order);
		keyValueMap.put("question.id", questionId);
		keyValueMap.put("state", EntityStateENUM.ACTIVE.toString());
        return findObjectsByKeyMap(Option.class,keyValueMap);
	}
}
