package com.core.service.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.constants.EntityStateENUM;
import com.core.dao.ExamDAO;
import com.core.dao.ExamSectionDAO;
import com.core.domain.lms.Exam;
import com.core.domain.lms.ExamSection;
import com.core.service.ExamSectionService;
import com.core.service.ExamService;
import com.core.stub.GameStub;

@Service("examSectionService")
public class ExamSectionServiceImpl implements ExamSectionService {

	@Autowired
	private ExamSectionDAO examSectionDAO;
	
	@Autowired
	private ExamService examService;

	public ExamSection getExamSection(Long examSectionId) {
		return GameStub.examSectionMap.get(examSectionId);

		// return examSectionDAO.findObjectById(examSectionId);
	}
	
    public List<ExamSection> showExamSections(Long examId){
    	return null;
    }
    
    public ExamSection saveExamSection(ExamSection examSection){
    	return examSectionDAO.saveOrUpdate(examSection);
    }
    public ExamSection findById(Long examSectionId){
    	
    	return examSectionDAO.findObjectById(examSectionId);
    	
    }
	
    public ExamSection addExamSection(Long examId, String examSectionName){
    	ExamSection examSection = new ExamSection();
    	examSection.setName(examSectionName);
    	examSection.setState(EntityStateENUM.INACTIVE.name());
    	Exam exam = examService.findById(examId);    	
    	examSection.setExam(exam);    	
    	examSection  = examSectionDAO.saveNew(examSection);
    	return examSection;
    }
	
	public ExamSection editExamSection(Long examSectionId, String examSectionName)
	{
		ExamSection examSection = this.findById(examSectionId);
		examSection.setName(examSectionName);
		return this.saveExamSection(examSection);
		 
		
	}
	
	public void deleteExamSection(Long examSectionId){
		
	}
	
	public List<ExamSection> findByNameAndExam(Long examId, String name){
		return examSectionDAO.findExamSectionByNameForAnExam(examId, name);
	}

}
