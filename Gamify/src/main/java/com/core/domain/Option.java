package com.core.domain;

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
@Table(name = "question_option")
@Proxy(lazy=false)
@FilterDef(name = Option.OPTION_FILTER,  parameters = @ParamDef(name = "state", type = "java.lang.String"))
@Filters( {
@Filter(name=Option.OPTION_FILTER, condition="state = :state")
} )
public class Option implements Serializable {
	
	public static final String OPTION_FILTER = "optionStateFilter";

	public Option(String text, String imageUrl, Integer ordr, Question question) {
		super();
		this.text = text;
		this.imageUrl = imageUrl;
		this.ordr = ordr;
		this.question = question;
	}

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public Option() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=1000)
	private String text;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "ordr")
	private Integer ordr;

	@JsonIgnore
	@ManyToOne(optional = false)
	@JoinColumn(name = "question_id")
	private Question question;

	@Column(name="state", nullable=false, columnDefinition = "character varying (20) default ACTIVE", length = 20)
    private String state;
	public String getState() {
		return state;
	}
	
	public Question fetchQuestion() {
		return question;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Option(Long id) {
		super();
		this.id = id;
	}

	public Integer getOrdr() {
		return ordr;
	}

	public void setOrdr(Integer ordr) {
		this.ordr = ordr;
	}

	/*
	 * public Question getQuestion() { return question; }
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
