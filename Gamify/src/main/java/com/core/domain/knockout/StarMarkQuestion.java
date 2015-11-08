package com.core.domain.knockout;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.core.domain.Question;

@Entity
@Table(name = "star_mark_question")
public class StarMarkQuestion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "question_id")
	private Question question; 
	
	@Column(name="user_id", nullable=false)
	private long userId;
	
	@Column(name="game_id", nullable=false)
	private long gameId;
	
	@Column(name="created_timestamp", nullable=false)
	private Long createdTimeStamp;

	public StarMarkQuestion() {
	}
	
	public StarMarkQuestion(Question question, long userId,long gameId) {
		super();
		this.question = question;
		this.userId = userId;
		this.gameId = gameId;
		this.createdTimeStamp = System.currentTimeMillis();
	}
	
	public StarMarkQuestion(long questionId, long userId,long gameId) {
		super();
		this.question = new Question(questionId);
		this.userId = userId;
		this.gameId = gameId;
		this.createdTimeStamp = System.currentTimeMillis();
	}


	public Question getQuestion() {
		return question;
	}


	public void setQuestion(Question question) {
		this.question = question;
	}


	public long getUserId() {
		return userId;
	}


	public void setUserId(long userId) {
		this.userId = userId;
	}


	public long getGameId() {
		return gameId;
	}


	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public Long getCreatedTimeStamp() {
		return createdTimeStamp;
	}


	public void setCreatedTimeStamp(Long createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}
	
}
