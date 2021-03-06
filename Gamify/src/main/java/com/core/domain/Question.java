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
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.Proxy;

import com.core.domain.lms.Topic;

@Entity
@Table(name = "question")
@Proxy(lazy=false)
@FilterDef(name = Question.QUESTION_FILTER,  parameters = @ParamDef(name = "state", type = "java.lang.String"))
@Filters( {
@Filter(name=Question.QUESTION_FILTER, condition="state = :state")
} )
public class Question implements Serializable {
	
	public static final String QUESTION_FILTER = "questionStateFilter";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "question_text",length=1000)
	private String questionText;
	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "max_time_to_answer_in_seconds", columnDefinition="int default 80")
	private int maxTimeToAnswerInSeconds;
	
	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Option> options;

	@JsonIgnore
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "topic_id")
	private Topic topic;
	@Column(name = "difficulty_level")
	private byte difficultyLevel;
	@Column(name="state", nullable=false, columnDefinition = "character varying (20) default ACTIVE", length = 20)
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

//	public Question(String questionText, String imageUrl, List<Option> options,
//			Topic topic, byte difficultyLevel) {
//		super();
//		this.questionText = questionText;
//		this.imageUrl = imageUrl;
//		this.options = options;
//		this.topic = topic;
//		this.difficultyLevel = difficultyLevel;
//	}
	
	
	

	public Long getId() {
		return id;
	}

	public Question(Long id, String questionText, String imageUrl,
		int maxTimeToAnswerInSeconds, List<Option> options, Topic topic,
		byte difficultyLevel, String state) {
	super();
	this.id = id;
	this.questionText = questionText;
	this.imageUrl = imageUrl;
	this.maxTimeToAnswerInSeconds = maxTimeToAnswerInSeconds;
	this.options = options;
	this.topic = topic;
	this.difficultyLevel = difficultyLevel;
	this.state = state;
}

	public int getMaxTimeToAnswerInSeconds() {
		return maxTimeToAnswerInSeconds;
	}

	public void setMaxTimeToAnswerInSeconds(int maxTimeToAnswerInSeconds) {
		this.maxTimeToAnswerInSeconds = maxTimeToAnswerInSeconds;
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
