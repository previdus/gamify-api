package com.core.domain.knockout;

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

import com.core.constants.IncorrectReasonENUM;
import com.core.domain.Question;

@Entity
@Table(name = "incorrect_reason_tool")
public class IncorrectReasonTool {
	
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
	
	@Enumerated(EnumType.STRING)
	@Column(name="reason", nullable=false, columnDefinition = "character varying (20) ", length = 40)
	private IncorrectReasonENUM reason;
	
	@Column(name="created_timestamp", nullable=false)
	private Long createdTimeStamp;

	
	public IncorrectReasonTool() {
	}
	
	public IncorrectReasonTool(Question question, long userId,long gameId, 
			IncorrectReasonENUM reason) {
		super();
		this.question = question;
		this.userId = userId;
		this.gameId = gameId;
		this.reason = reason;
		this.createdTimeStamp = System.currentTimeMillis();
	}

	public long getId() {
		return id;
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


	public IncorrectReasonENUM getReason() {
		return reason;
	}


	public void setReason(IncorrectReasonENUM reason) {
		this.reason = reason;
	}


	public Long getCreatedTimeStamp() {
		return createdTimeStamp;
	}


	public void setCreatedTimeStamp(Long createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}
	
	
	
	
	
	

}
