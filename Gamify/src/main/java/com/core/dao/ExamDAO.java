package com.core.dao;

import java.io.Serializable;

import java.util.List;

import com.core.dao.generic.GenericRepository;
import com.core.domain.lms.Exam;

public interface ExamDAO extends GenericRepository<Exam, Serializable>{
	
	public Exam findExamByName(String name);
	public List<Exam> findActiveExams();
	public List<Exam> findInActiveExams();
	
	

}
