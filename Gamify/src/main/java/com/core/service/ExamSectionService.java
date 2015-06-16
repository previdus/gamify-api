package com.core.service;

import java.util.List;

import com.core.domain.lms.Exam;
import com.core.domain.lms.ExamSection;

public interface ExamSectionService {
	public ExamSection getExamSection(Long examSectionId);
    
	public List<ExamSection> showExamSections(Long examId);
	
	public ExamSection addExamSection(Long examId, String examSectionName);
	
	public ExamSection editExamSection(Long examSectionId, String examSectionName);
	
	public void deleteExamSection(Long examSectionId);
	
	public List<ExamSection> findByNameAndExam(Long examId, String name);
	
	public ExamSection findById(Long examSectionId);
	
	public ExamSection saveExamSection(ExamSection examSection);
	
}
