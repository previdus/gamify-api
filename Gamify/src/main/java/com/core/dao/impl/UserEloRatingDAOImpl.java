package com.core.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.core.dao.UserEloRatingDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.User;
import com.core.domain.knockout.UserEloRating;

@Repository("userEloRatingDAO")
public class UserEloRatingDAOImpl 
extends HibernateGenericRepository<UserEloRating, Serializable> 
implements UserEloRatingDAO {
	
	public UserEloRating getUserEloRatingByUserId(Long userId) {
		UserEloRating userEloRating = null;
		Session session = getSession();
		Query qry = session.createQuery("from UserEloRating where user_id = :uid")
				.setParameter("uid", userId);
		List<UserEloRating> userEloRatingList = qry.list();
		if (userEloRatingList != null && userEloRatingList.size() > 0) {
			releaseSession(session);
			return userEloRatingList.get(0);
		}
		releaseSession(session);
		return null;

	}
	

}
