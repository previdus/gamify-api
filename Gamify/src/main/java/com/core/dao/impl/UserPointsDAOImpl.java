package com.core.dao.impl;

import java.io.Serializable;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.core.dao.UserPointsDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.UserPoints;

@Repository("userPointsDAO")
public class UserPointsDAOImpl  extends
HibernateGenericRepository<UserPoints, Serializable> implements
UserPointsDAO {

private static final Logger log = LoggerFactory.getLogger(UserPointsDAOImpl.class);

public void addPoints(long userId, int points) {
  Session session =  getSession();
  UserPoints userPoints = (UserPoints) session.get(UserPoints.class, userId);
  if(userPoints == null)
	  session.saveOrUpdate(new UserPoints(userId, points)); 
  else{
	Query qry = session.createQuery(
			"update UserPoints set  lmsPoints = lmsPoints + :points where userId = :userId").setParameter(
			"points", points).setParameter("userId", userId);
			qry.list();
  }
			releaseSession(session);
}

}
