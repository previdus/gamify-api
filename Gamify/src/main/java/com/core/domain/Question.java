package com.core.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.core.domain.lms.Topic;

@Entity
@Table(name = "question")
@Proxy(lazy=false)
public class Question implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "question_text")
	private String questionText;
	@Column(name = "image_url")
	private String imageUrl;

	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Option> options;

	@JsonIgnore
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "topic_id")
	private Topic topic;
	@Column(name = "difficulty_level")
	private byte difficultyLevel;
	
	@Column(name="state")
    private String state;
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Question() {
		super();
	}

	public Question(Long id) {
		super();
		this.id = id;
	}

	public Question(String questionText, String imageUrl, List<Option> options,
			Topic topic, byte difficultyLevel) {
		super();
		this.questionText = questionText;
		this.imageUrl = imageUrl;
		this.options = options;
		this.topic = topic;
		this.difficultyLevel = difficultyLevel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public Topic fetchTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public byte getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(byte difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

}
