package com.core.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.constants.EntityStateENUM;
import com.core.dao.ExamDAO;
import com.core.dao.RoomDAO;
import com.core.domain.lms.Exam;
import com.core.domain.lms.Room;
import com.core.service.ExamService;
import com.core.service.RoomService;
import com.googlecode.ehcache.annotations.Cacheable;

@Service("examService")
public class ExamServiceImpl implements ExamService {

	
	
	@Autowired
	private ExamDAO examDAO;
	
	@Cacheable(cacheName = "exams")
	public List<Exam> showExams(){
		
		return examDAO.findAll();
		
	}
	
    public Exam addExam(String examName){
    	Exam exam = new Exam();
    	exam.setExamName(examName);
    	exam.setState(EntityStateENUM.INACTIVE.name());
    	exam  = examDAO.saveNew(exam);
    	return exam;
    }
	
	public Exam editExam(Long examId, String examName){
		Exam exam = examDAO.findObjectById(examId);
		exam.setExamName(examName);
		exam  = examDAO.saveOrUpdate(exam);
		return exam;
	}
	
	public void deleteExam(Long examId){
		Exam exam = examDAO.findObjectById(examId);
		examDAO.delete(exam);
	}	
	
	public Exam findByName(String name){
		return examDAO.findExamByName(name);
	}
	
	public Exam findById(Long id){
		return examDAO.findObjectById(id);
	}
	
	public Exam saveExam(Exam exam){
		return examDAO.saveOrUpdate(exam);
	}
	
	
}
