package com.core.domain.knockout;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.core.domain.User;
import com.core.domain.lms.Exam;
import com.core.domain.lms.ExamSection;
import com.core.domain.lms.Topic;

@Embeddable
public class UserExam implements Serializable {
	
	@ManyToOne(optional=false)
    @JoinColumn(name = "user_id")
    private User user;

	@ManyToOne(optional=false)
    @JoinColumn(name = "exam_id")
    private Exam exam;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	

	

    //getter, setter methods
}