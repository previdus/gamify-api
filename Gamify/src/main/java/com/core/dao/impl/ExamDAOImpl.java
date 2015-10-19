package com.core.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.core.constants.EntityStateENUM;
import com.core.constants.GameConstants;
import com.core.dao.ExamDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.Option;
import com.core.domain.Question;
import com.core.domain.lms.Exam;
import com.core.domain.lms.ExamSection;
import com.core.domain.lms.Topic;

@Repository("examDAO")

public class ExamDAOImpl extends HibernateGenericRepository<Exam, Serializable> implements ExamDAO{
	
	private static final Logger log = LoggerFactory
			.getLogger(ExamDAOImpl.class);
	
	public Exam findExamByName(String name){
	          return findObjectByKey(Exam.class, "examName", name);
	}
	
	public List<Exam> findActiveExams(){
		Session session = getSession();
		Filter filter = session.enableFilter(Exam.ACTIVE_EXAMS);
		filter.setParameter("activeState", EntityStateENUM.ACTIVE.toString());
		Query query = session.createQuery("from Exam");
		List<Exam> exams = query.list();
		session.disableFilter(Exam.ACTIVE_EXAMS);
		releaseSession(session);
		return exams;
		//return findObjectsByKeys(Exam.class, "state", EntityStateENUM.ACTIVE.toString().name());
	}
	
	public List<Exam> findInActiveExams(){
		return findObjectsByKeys(Exam.class, "state", EntityStateENUM.INACTIVE.name());
	}
	
	public List<Exam> findExamsWithAllActiveEntities(){

		List<Exam> exams = new ArrayList<Exam>();
		try{

			Session session = this.getSession();
			Transaction transaction =  session.beginTransaction();

			enableFilters(session);
			
			exams = session.createCriteria(Exam.class).list(); 
			

			//System.out.println("ACTIVE Exams :------------------------------------------------------- " + exams.size());
			transaction.commit();
			disableFilters(session);
			releaseSession(session);
			

		}catch(Exception e){
			e.printStackTrace();
			return exams;
		}

		return exams;
	}

	private void disableFilters(Session session) {
		session.disableFilter(Exam.ACTIVE_EXAMS);
		session.disableFilter(ExamSection.ACTIVE_EXAM_SECTIONS);
		session.disableFilter(Topic.TOPIC_FILTER);
		session.disableFilter(Question.QUESTION_FILTER);
		session.disableFilter(Option.OPTION_FILTER);
	}

	private void enableFilters(Session session) {
		Filter filter = session.enableFilter(Exam.ACTIVE_EXAMS);
		filter.setParameter("state", EntityStateENUM.ACTIVE.toString());
		
		Filter sectionFilter = session.enableFilter(ExamSection.ACTIVE_EXAM_SECTIONS);
		sectionFilter.setParameter("state", EntityStateENUM.ACTIVE.toString());
		
		Filter topicFilter = session.enableFilter(Topic.TOPIC_FILTER);
		topicFilter.setParameter("state", EntityStateENUM.ACTIVE.toString());
		
		Filter questionFilter = session.enableFilter(Question.QUESTION_FILTER);
		questionFilter.setParameter("state", EntityStateENUM.ACTIVE.toString());
		
		Filter optionFilter = session.enableFilter(Option.OPTION_FILTER);
		optionFilter.setParameter("state", EntityStateENUM.ACTIVE.toString());
	}
	
	public List<Exam> findExamByState(String examState,String examSectionState,String topicState){
		
		List<Exam> exams = new ArrayList<Exam>();
		
		try{
			
			Session session = this.getSession();
			Transaction transaction =  session.beginTransaction();

			Filter filter = session.enableFilter(Exam.ACTIVE_EXAMS);
			filter.setParameter("state", examState);
			
			Filter sectionFilter = session.enableFilter(ExamSection.ACTIVE_EXAM_SECTIONS);
			sectionFilter.setParameter("state", examSectionState);
			
			Filter topicFilter = session.enableFilter(Topic.TOPIC_FILTER);
			topicFilter.setParameter("state", topicState);
			
			exams = session.createCriteria(Exam.class).list(); 
			

			log.info("ACTIVE Exams : " + exams);
			transaction.commit();
			disableFilters(session);
			releaseSession(session);
			
			
		}catch(Exception e){
			e.printStackTrace();
			return exams; 
		}
		
		
		return exams;
	}

}
