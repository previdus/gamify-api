package com.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.dao.ExamSectionDAO;
import com.core.domain.lms.ExamSection;
import com.core.service.ExamSectionService;
import com.core.stub.GameStub;

@Service("examSectionService")
public class ExamSectionServiceImpl implements ExamSectionService {

	@Autowired
	private ExamSectionDAO examSectionDAO;

	public ExamSection getExamSection(Long examSectionId) {
		return GameStub.examSectionMap.get(examSectionId);

		// return examSectionDAO.findObjectById(examSectionId);
	}

}
