package com.core.domain.lms;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "exam_section")
public class ExamSection implements Serializable {

	@Id
	private Long id;
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "examsection_topic_mapping", joinColumns = { @JoinColumn(name = "exam_section_id") }, inverseJoinColumns = { @JoinColumn(name = "topic_id") })
	private List<Topic> topics;

	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "exam_id")
	private Exam exam;

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
