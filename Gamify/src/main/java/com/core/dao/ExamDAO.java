package com.core.dao;

import java.io.Serializable;

import java.util.List;

import com.core.dao.generic.GenericRepository;
import com.core.domain.lms.Exam;

public interface ExamDAO extends GenericRepository<Exam, Serializable>{
	
	public Exam findExamByName(String name);
	public List<Exam> findActiveExams();
	public List<Exam> findInActiveExams();
	
	public List<Exam> findAllActiveExams();
	public List<Exam> findExamByState(String examState,String examSectionState,String topicState);
	
	

}
