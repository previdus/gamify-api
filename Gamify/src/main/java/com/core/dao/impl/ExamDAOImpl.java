package com.core.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
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
		return findObjectsByKeys(Exam.class, "state", EntityStateENUM.ACTIVE.name());
	}
	
	public List<Exam> findInActiveExams(){
		return findObjectsByKeys(Exam.class, "state", EntityStateENUM.INACTIVE.name());
	}
 
}
