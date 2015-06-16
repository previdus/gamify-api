package com.core.dao;

import java.io.Serializable;
import java.util.List;

import com.core.dao.generic.GenericRepository;
import com.core.domain.lms.ExamSection;

public interface ExamSectionDAO extends
		GenericRepository<ExamSection, Serializable> {
	
	public  List<ExamSection> findExamSectionByNameForAnExam(Long examId, String name);
	

}
