package com.core.service;

import java.util.List;

import com.core.domain.Question;
import com.core.domain.lms.Exam;
import com.core.domain.lms.ExamSection;
import com.core.domain.lms.Topic;

public interface ExamService {
	
	public Exam findById(Long id);
	
	public Exam findByName(String name);
	
	public Exam addExam(String examName);
	
	public Exam editExam(Long examId, String examName);
	
	public void deleteExam(Long examId);
	
	public List<Exam> showExams();	
	
	public Exam saveExam(Exam exam);
	 

}
