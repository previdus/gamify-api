package com.core.domain.knockout;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.core.domain.User;
import com.core.domain.lms.ExamSection;
import com.core.domain.lms.Topic;

@Embeddable
public class UserExamSection implements Serializable {
	
	@ManyToOne(optional=false)
    @JoinColumn(name = "user_id")
    private User user;

	@ManyToOne(optional=false)
    @JoinColumn(name = "exam_section_id")
    private ExamSection examSection;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ExamSection getExamSection() {
		return examSection;
	}

	public void setExamSection(ExamSection examSection) {
		this.examSection = examSection;
	}

	

    //getter, setter methods
}