package com.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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

import com.core.constants.EntityStateENUM;


@Entity
@Table(name = "question_option")
@Proxy(lazy=false)
@FilterDef(name = Option.OPTION_FILTER,  parameters = @ParamDef(name = "state", type = "com.core.constants.EntityStateENUM"))
@Filters( {
@Filter(name=Option.OPTION_FILTER, condition="state = :state")
} )
public class Option implements Serializable {
	
	public static final String OPTION_FILTER = "optionStateFilter";

//	public Option(String text, String imageUrl, Integer ordr, Question question) {
//		super();
//		this.text = text;
//		this.imageUrl = imageUrl;
//		this.ordr = ordr;
//		this.question = question;
//	}
	
	

	public Option(String text, String imageUrl, Integer ordr,
			Question question, EntityStateENUM state) {
		super();
		this.text = text;
		this.imageUrl = imageUrl;
		this.ordr = ordr;
		this.question = question;
		this.state = state;
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
	

	@Column(name="text")
	private String text;


	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "ordr")
	private Integer ordr;

	@JsonIgnore
	@ManyToOne(optional = false , fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	private Question question;

	@Enumerated(EnumType.STRING)
	@Column(name="state", nullable=false, columnDefinition = "character varying (20) default ACTIVE", length = 20)
    private EntityStateENUM state;
	
	
	public EntityStateENUM getState() {
		return state;
	}
	
	public Question fetchQuestion() {
		return question;
	}

	public void setState(EntityStateENUM state) {
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
