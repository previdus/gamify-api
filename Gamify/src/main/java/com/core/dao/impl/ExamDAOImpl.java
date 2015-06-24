package com.core.dao.impl;

import java.io.Serializable;
import java.util.List;





import org.hibernate.Filter;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.core.constants.EntityStateENUM;
import com.core.dao.ExamDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.lms.Exam;

@Repository("examDAO")

public class ExamDAOImpl extends HibernateGenericRepository<Exam, Serializable> implements ExamDAO{
	
	public Exam findExamByName(String name){
	          return findObjectByKey(Exam.class, "examName", name);
	}
	
	public List<Exam> findActiveExams(){
		Filter filter = this.getSession().enableFilter(Exam.ACTIVE_EXAMS);
		filter.setParameter("activeState", "ACTIVE");
		Query query = this.getSession().createQuery("from Exam");
		List<Exam> exams = query.list();
		this.getSession().disableFilter(Exam.ACTIVE_EXAMS);
		return exams;
		//return findObjectsByKeys(Exam.class, "state", EntityStateENUM.ACTIVE.name());
	}
	
	public List<Exam> findInActiveExams(){
		return findObjectsByKeys(Exam.class, "state", EntityStateENUM.INACTIVE.name());
	}

}
