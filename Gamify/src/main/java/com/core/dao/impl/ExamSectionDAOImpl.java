package com.core.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.core.dao.ExamSectionDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.lms.Exam;
import com.core.domain.lms.ExamSection;

@Repository("examSectionDAO")
public class ExamSectionDAOImpl extends
		HibernateGenericRepository<ExamSection, Serializable> implements
		ExamSectionDAO {

	public List<ExamSection> findExamSectionByNameForAnExam(Long examId, String name){
		Map<String, Object> keyValueMap = new HashMap<String, Object>();
		keyValueMap.put("name", name);
		keyValueMap.put("exam.id", examId);
        return findObjectsByKeyMap(ExamSection.class,keyValueMap);
    }
}
