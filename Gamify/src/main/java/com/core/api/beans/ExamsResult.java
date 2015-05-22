package com.core.api.beans;

import java.util.List;

import com.core.domain.lms.Exam;

public class ExamsResult extends ApiResult {

	private List<Exam> exams;

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}

	public List<Exam> getExams() {
		return exams;
	}
	
	

}
