package com.core.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.core.constants.GameConstants;
import com.core.dao.UserEloRatingPerExamDAO;
import com.core.dao.UserEloRatingPerTopicDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.User;
import com.core.domain.knockout.UserEloRatingPerExam;
import com.core.domain.knockout.UserEloRatingPerExamSection;
import com.core.domain.knockout.UserEloRatingPerTopic;

@Repository("userEloRatingPerExamDAO")
public class UserEloRatingPerExamDAOImpl 
extends HibernateGenericRepository<UserEloRatingPerExam, Serializable> 
implements UserEloRatingPerExamDAO {
	
	
	
public UserEloRatingPerExam getUserEloRatingPerExam(Long userId, Long examId) {
		
		Session session = getSession();
		Query qry = session.createQuery("from UserEloRatingPerExam t where t.userExam.user.id = :uid and t.userExam.exam.id = :examId")
				.setParameter("uid", userId)
				.setParameter("examId", examId);
		List<UserEloRatingPerExam> userEloRatingExamList = qry.list();
		if (userEloRatingExamList != null && userEloRatingExamList.size() > 0) {
			releaseSession(session);
			return userEloRatingExamList.get(0);
		}
		releaseSession(session);
		return null;

	}
	
	public List<UserEloRatingPerExam> getTopUserEloRatingsPerExam(Integer noOfPlayersToShow, Long examId, boolean excludeProvisional) {
		List<UserEloRatingPerExam> userEloRatingExamList = null;
		Session session = getSession();
		Query qry = session.createQuery("from UserEloRatingPerExam t "+		
		" where t.userExam.exam.id = :examId "
		+((excludeProvisional)?(" and t.noOfQuestionsAttempted > "+GameConstants.PROVISIONAL_LIMIT_FOR_ELO_RATING):"") 
		+" order by t.eloRating desc limit "+noOfPlayersToShow).setParameter("examId", examId);				
		userEloRatingExamList = qry.list();		
		releaseSession(session);
		return userEloRatingExamList;

	}
	

}
