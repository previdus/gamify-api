package com.core.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.core.api.beans.StudentStats;
import com.core.dao.PlayerResponseLogDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.knockout.PlayerResponseLog;

@Repository("playerResponseLogDAO")
public class PlayerResponseLogDAOImpl extends
		HibernateGenericRepository<PlayerResponseLog, Serializable> implements
		PlayerResponseLogDAO {

	private static final Logger log = LoggerFactory.getLogger(PlayerResponseLogDAOImpl.class);

	public StudentStats getQuestionStats(long userId, long topicId) {
		Session session = getSession();
		Query query = session.createQuery(
				"SELECT count(*), sum(questionWinner), sum(responseCorrect), avg(timeTakenToAnswer) FROM PlayerResponseLog "
				+ "where user.id = " +  userId + " and topic_id = " + topicId);
		List<Object[]> listResult = query.list();
		 StudentStats stats = new StudentStats();
		 
		 if(listResult != null && listResult.size() > 0){
			 Object[] aRow = listResult.get(0);
			stats.setNoOfQuestionsAttempted((Integer)aRow[0]);
			stats.setNoOfQuestionsAnsweredInBestTime((Integer)aRow[1]);
			stats.setNoOfQuestionsAnsweredItCorrect((Integer)aRow[2]);
			stats.setAverageTimeToAnswer((Double)aRow[2]);
		 }
		releaseSession(session);
		return stats;
	}
	
	public StudentStats getQuestionAverageStats(long topicId) {
		Session session = getSession();
		Query query = session.createQuery(
				"SELECT count(*), sum(questionWinner), sum(responseCorrect), avg(timeTakenToAnswer) FROM PlayerResponseLog "
				+ "where topic_id = " + topicId);
		List<Object[]> listResult = query.list();
		 StudentStats stats = new StudentStats();
		 
		 if(listResult != null && listResult.size() > 0){
			 Object[] aRow = listResult.get(0);
			stats.setNoOfQuestionsAttempted((Integer)aRow[0]);
			stats.setNoOfQuestionsAnsweredInBestTime((Integer)aRow[1]);
			stats.setNoOfQuestionsAnsweredItCorrect((Integer)aRow[2]);
			stats.setAverageTimeToAnswer((Double)aRow[2]);
		 }
		releaseSession(session);
		return stats;
	}

	
}
