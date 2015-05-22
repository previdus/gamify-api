package com.core.domain.lms;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "exam")
public class Exam implements Serializable {

	@Id
	private Long id;
	@Column(name = "exam_name")
	private String examName;
	@Column(name = "exam_image_name")
	private String examImageName;

	@OneToMany(mappedBy = "exam", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ExamSection> examSections;

	public Exam(Long id) {
		super();
		this.id = id;
	}

	public Exam() {
		super();
	}

	public Exam(Long id, String examName, String examImageName,
			List<ExamSection> examSections) {
		super();
		this.id = id;
		this.examName = examName;
		this.examImageName = examImageName;
		this.examSections = examSections;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExamName() {
		return examName;
	}

	public String getExamImageName() {
		return examImageName;
	}

	public void setExamImageName(String examImageName) {
		this.examImageName = examImageName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public List<ExamSection> getExamSections() {
		return examSections;
	}

	public void setExamSections(List<ExamSection> examSections) {
		this.examSections = examSections;
	}

}
