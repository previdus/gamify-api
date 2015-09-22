package com.core.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.core.constants.GameConstants;
import com.core.dao.UserEloRatingPerExamSectionDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.knockout.UserEloRatingPerExamSection;


@Repository("userEloRatingPerExamSectionDAO")
public class UserEloRatingPerExamSectionDAOImpl 
extends HibernateGenericRepository<UserEloRatingPerExamSection, Serializable> 
implements UserEloRatingPerExamSectionDAO {
	
	
    public UserEloRatingPerExamSection getUserEloRatingPerExamSection(Long userId, Long examSectionId) {
		
		Session session = getSession();
		Query qry = session.createQuery("from UserEloRatingPerExamSection t where t.userExamSection.user.id = :uid and t.userExamSection.examSection.id = :examSectionId")
				.setParameter("uid", userId)
				.setParameter("examSectionId", examSectionId);
		List<UserEloRatingPerExamSection> userEloRatingExamSectionList = qry.list();
		if (userEloRatingExamSectionList != null && userEloRatingExamSectionList.size() > 0) {
			releaseSession(session);
			return userEloRatingExamSectionList.get(0);
		}
		releaseSession(session);
		return null;

	}
	
	public List<UserEloRatingPerExamSection> getTopUserEloRatingsPerExamSection(Integer noOfPlayersToShow, Long examSectionId, boolean excludeProvisional) {
		List<UserEloRatingPerExamSection> userEloRatingExamSectionList = null;
		Session session = getSession();
		Query qry = session.createQuery("from UserEloRatingPerExamSection t "+		
		" where t.userExamSection.examSection.id = :examSectionId "
		+((excludeProvisional)?(" and t.noOfQuestionsAttempted > "+GameConstants.PROVISIONAL_LIMIT_FOR_ELO_RATING):"") 
		+" order by t.eloRating desc limit "+noOfPlayersToShow).setParameter("examSectionId", examSectionId);				
		userEloRatingExamSectionList = qry.list();		
		releaseSession(session);
		return userEloRatingExamSectionList;

	}
	


}
