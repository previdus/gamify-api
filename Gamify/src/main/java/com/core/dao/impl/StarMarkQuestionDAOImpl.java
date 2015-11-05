package com.core.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.core.dao.StarMarkQuestionDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.knockout.StarMarkQuestion;

@Repository("starMarkQuestionDAO")
public class StarMarkQuestionDAOImpl extends
HibernateGenericRepository<StarMarkQuestion, Serializable> implements StarMarkQuestionDAO{

	
	public StarMarkQuestion get(long gameId, long userId, long questionId) {
		
		Session session = getSession();
		Criteria cr = session.createCriteria(StarMarkQuestion.class);
		cr.add(Restrictions.eq("userId", userId));
		cr.add(Restrictions.eq("gameId", gameId));
		cr.add(Restrictions.eq("question.id", questionId));
		List results = cr.list();
		releaseSession(session);
		if(results == null || results.size() == 0)
			return null;
		return (StarMarkQuestion) results.get(0);
	}

}
