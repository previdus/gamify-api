package com.core.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.core.dao.IncorrectReasonToolDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.knockout.IncorrectReasonTool;

@Repository("incorrectReasonToolDAO")
public class IncorrectReasonToolDAOImpl extends
HibernateGenericRepository<IncorrectReasonTool, Serializable> implements IncorrectReasonToolDAO{
	
public IncorrectReasonTool get(long gameId, long userId, long questionId) {
		
		Session session = getSession();
		Criteria cr = session.createCriteria(IncorrectReasonTool.class);
		cr.add(Restrictions.eq("userId", userId));
		cr.add(Restrictions.eq("gameId", gameId));
		cr.add(Restrictions.eq("question.Id", questionId));
		List results = cr.list();
		releaseSession(session);
		if(results == null || results.size() == 0)
			return null;
		return (IncorrectReasonTool) results.get(0);
	}

}
