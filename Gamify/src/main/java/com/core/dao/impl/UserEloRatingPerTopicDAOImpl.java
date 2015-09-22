package com.core.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.core.constants.GameConstants;
import com.core.dao.UserEloRatingPerTopicDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.User;
import com.core.domain.knockout.UserEloRatingPerExam;
import com.core.domain.knockout.UserEloRatingPerExamSection;
import com.core.domain.knockout.UserEloRatingPerTopic;

@Repository("userEloRatingPerTopicDAO")
public class UserEloRatingPerTopicDAOImpl 
extends HibernateGenericRepository<UserEloRatingPerTopic, Serializable> 
implements UserEloRatingPerTopicDAO {
	
	public UserEloRatingPerTopic getUserEloRatingPerTopic(Long userId, Long topicId) {
		
		Session session = getSession();
		Query qry = session.createQuery("from UserEloRatingPerTopic t where t.userTopic.user.id = :uid and t.userTopic.topic.id = :topicId")
				.setParameter("uid", userId)
				.setParameter("topicId", topicId);
		List<UserEloRatingPerTopic> userEloRatingTopicList = qry.list();
		if (userEloRatingTopicList != null && userEloRatingTopicList.size() > 0) {
			releaseSession(session);
			return userEloRatingTopicList.get(0);
		}
		releaseSession(session);
		return null;

	}
	
	public List<UserEloRatingPerTopic> getTopUserEloRatingsPerTopic(Integer noOfPlayersToShow, Long topicId, boolean excludeProvisional) {
		List<UserEloRatingPerTopic> userEloRatingTopicList = null;
		Session session = getSession();
		Query qry = session.createQuery("from UserEloRatingPerTopic t "+		
		" where t.userTopic.topic.id = :topicId "
		+((excludeProvisional)?(" and t.noOfQuestionsAttempted > "+GameConstants.PROVISIONAL_LIMIT_FOR_ELO_RATING):"") 
		+" order by t.eloRating desc limit "+noOfPlayersToShow).setParameter("topicId", topicId);				
		userEloRatingTopicList = qry.list();		
		releaseSession(session);
		return userEloRatingTopicList;

	}
	
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
