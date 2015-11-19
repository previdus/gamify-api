package com.core.dao.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.core.api.beans.TotalNoOfPointsScoredByUser;
import com.core.dao.UserPointsDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.knockout.UserPoints;

@Repository("userPointsDAO")
public class UserPointsDAOImpl extends HibernateGenericRepository<UserPoints, Serializable>
		implements UserPointsDAO {

	public List<TotalNoOfPointsScoredByUser> getTopPersonsWhoScoredMaxPointsInLMS(long topicId) {
		
		Session session = getSession();
		Query query = session.createQuery(
				" select  u.displayName as name,u.imageUrl as imageUrl, sum(up.pointsWon) as points from UserPoints up, User u "
				+ " where up.user.id = u.id and up.topicId = " + topicId
				+ " group by (up.user.id ) order by points desc limit 5");
		List<Object[]> listResult = query.list();
		 List<TotalNoOfPointsScoredByUser> winningUsers = new LinkedList<TotalNoOfPointsScoredByUser>();
		for (Object[] aRow : listResult) {
			
			
			winningUsers.add(new TotalNoOfPointsScoredByUser((String)aRow[0], (String)aRow[1], (Long)aRow[2]));
		}
		releaseSession(session);
		return winningUsers;
		
	}
	

		


}
