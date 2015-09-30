package com.core.dao.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.core.api.beans.UserEloRatingDTO;
import com.core.constants.GameConstants;
import com.core.dao.UserEloRatingPerTopicDAO;
import com.core.dao.generic.HibernateGenericRepository;
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
	
	public List<UserEloRatingDTO> getTopUserEloRatingsPerExamSection(Integer noOfPlayersToShow, Long examSectionId) {
		List<UserEloRatingDTO> userEloRatingExamSectionList = new LinkedList<UserEloRatingDTO>();
		Session session = getSession();
		Query qry = session.createSQLQuery("select f.user_id , h.display_name, h.image_url, f.elo, f.topicCount from "
				+ "(select d.* from (select c.user_id, avg(c.elo_rating) as elo, count(c.topic_id) as topicCount from "
				+ "( select a.user_id, a.elo_rating,a.topic_id from user_elo_rating_topic a, topic b "
				+ "where a.topic_id = b.id and b.exam_section_id = :examSectionId and a.no_of_questions_attempted > :provisiional_limit) c"
				+ " group by c.user_id)d, (select count(id) idCount, exam_section_id from topic group by exam_section_id) e "
				+ "where d.topicCount = e.idCount and e.exam_section_id = :examSectionId) f, (select id, display_name, image_url from user) h "
				+ "where f.user_id = h.id order by f.elo desc limit "+noOfPlayersToShow)
				.setParameter("examSectionId", examSectionId)
				.setParameter("provisiional_limit", GameConstants.PROVISIONAL_LIMIT_FOR_ELO_RATING);				
		List<Object[]>  objectList = qry.list();
		int index = 1;
		for(Object[] o: objectList){
			
			UserEloRatingDTO dto =  new UserEloRatingDTO();
			dto.setDisplayName(o[1].toString());
			dto.setUserId(Long.parseLong(o[0].toString()));
			dto.setEloRating((int)Double.parseDouble(o[3].toString()));
			dto.setImageUrl((o[2] != null)?o[2].toString():null);
			dto.setNumberOfTopicsCovered(Integer.parseInt(o[4].toString()));
			double percentile = (objectList.size() - index)*100/objectList.size();
			index++;
			dto.setPercentile(percentile);
			userEloRatingExamSectionList.add(dto);
			
		}
		
		releaseSession(session);
		return userEloRatingExamSectionList;

	}
	
	public List<UserEloRatingDTO> getTopUserEloRatingsPerExam(Integer noOfPlayersToShow, Long examId) {
		List<UserEloRatingDTO> userEloRatingExamList = new LinkedList<UserEloRatingDTO>();
		Session session = getSession();
		Query qry = session.createSQLQuery("select f.user_id, h.display_name, h.image_url, f.elo, f.examSectionCount from "
				+ "(select d.user_id, e.exam_id, avg(d.elo) as elo, count(d.exam_section_id) as examSectionCount from "
				+ "(select c.user_id, c.exam_section_id , avg(c.elo_rating) as elo, count(c.topic_id) from "
				+ "( select a.user_id, a.elo_rating,a.topic_id, b.exam_section_id from user_elo_rating_topic a, topic b "
				+ "where a.topic_id = b.id  and a.no_of_questions_attempted > :provisiional_limit) c "
				+ "group by c.user_id)d, exam_section e where d.exam_section_id = e.id and e.exam_id = :examId "
				+ "group by d.user_id) f, (select exam_id, count(id) as idCount from exam_section  group by exam_id) g, "
				+ "(select id, display_name, image_url from user) h "
				+ "where f.exam_id = g.exam_id and f.examSectionCount = g.idCount "
				+ "and f.user_id = h.id order by f.elo desc "
				+ "limit "+noOfPlayersToShow)
				.setParameter("examId", examId)
				.setParameter("provisiional_limit", GameConstants.PROVISIONAL_LIMIT_FOR_ELO_RATING);				
		List<Object[]>  objectList = qry.list();
		int index = 1;
		for(Object[] o: objectList){
			
			UserEloRatingDTO dto =  new UserEloRatingDTO();
			dto.setDisplayName(o[1].toString());
			dto.setUserId(Long.parseLong(o[0].toString()));
			dto.setEloRating((int)Double.parseDouble(o[3].toString()));
			dto.setImageUrl((o[2] != null)?o[2].toString():null);
			dto.setNumberOfExamSectionsCovered(Integer.parseInt(o[4].toString()));
			double percentile = (objectList.size() - index)*100/objectList.size();
			index++;
			dto.setPercentile(percentile);
			userEloRatingExamList.add(dto);
			
		}
		
		releaseSession(session);
		return userEloRatingExamList;

	}
	
	

}
