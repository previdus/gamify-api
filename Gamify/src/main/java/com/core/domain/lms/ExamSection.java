package com.core.domain.lms;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "exam_section")
@Proxy(lazy=false)

public class ExamSection implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;


	
	@OneToMany(mappedBy="examSection", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)	
	private List<Topic> topics;

	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "exam_id")
	private Exam exam;
	
	@Column(name="state", nullable=false, columnDefinition = "character varying (20) default ACTIVE", length = 20)
	private String state;

	public String getState() {
		return state;
	}

	public Exam getExam() {
		return exam;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ExamSection(Long id, String name, List<Topic> topics, Exam exam) {
		super();
		this.id = id;
		this.name = name;
		this.topics = topics;
		this.exam = exam;
	}

	public ExamSection() {
		super();
	}

	public ExamSection(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public Exam fetchAssociatedExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

}
