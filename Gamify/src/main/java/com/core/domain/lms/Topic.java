package com.core.domain.lms;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "topic")
@Proxy(lazy=false)
@FilterDef(name = Topic.TOPIC_FILTER,  parameters = @ParamDef(name = "state", type = "java.lang.String"))
@Filters( {
@Filter(name=Topic.TOPIC_FILTER, condition="state = :state")
} )
public class Topic implements Serializable {
	
	public static final String TOPIC_FILTER = "topicStateFilter";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	@Column(name="state", nullable=false, columnDefinition = "character varying (20) default ACTIVE", length = 20)
	private String state;
	
	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "exam_section_id")
	private ExamSection examSection;

	public ExamSection fetchExamSection() {
		return examSection;
	}

	public void setExamSection(ExamSection examSection) {
		this.examSection = examSection;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Topic(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Topic(Long id) {
		super();
		this.id = id;
	}

	public Topic() {
		super();
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

}
