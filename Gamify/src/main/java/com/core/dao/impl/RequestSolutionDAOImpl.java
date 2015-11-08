package com.core.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.core.dao.RequestSolutionDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.Question;
import com.core.domain.knockout.RequestSolution;

@Repository("requestSolutionDAO")
public class RequestSolutionDAOImpl extends
HibernateGenericRepository<RequestSolution, Serializable> implements RequestSolutionDAO{

	
	public RequestSolution get(long gameId, long userId, long questionId) {
		
		Session session = getSession();
		Criteria cr = session.createCriteria(RequestSolution.class);
		cr.add(Restrictions.eq("userId", userId));
		cr.add(Restrictions.eq("gameId", gameId));
		cr.add(Restrictions.eq("question", new Question(questionId)));
		List results = cr.list();
		releaseSession(session);
		if(results == null || results.size() == 0)
			return null;
		return (RequestSolution) results.get(0);
	}

}
