package com.core.domain.knockout;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.core.domain.Option;
import com.core.domain.User;

@Entity
@Table(name="player_response_log")
public class PlayerResponseLog implements Comparable<PlayerResponseLog>, Serializable{
	
	@Id
	@GeneratedValue
	private Long id;
	
	private transient Player player;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="response_option_id")
	private Option response;
	
	@Column(name="time_taken_to_answer")
    private Long timeTakenToAnswer;
	private Integer rank;
	
	//@Column(name="question_id")
	//private long questionId;
	
	@Column(name="is_response_correct", columnDefinition="boolean default false")
	private boolean responseCorrect;
		
	@Column(name="is_question_winner", columnDefinition="boolean default false")
	private boolean questionWinner;
	
	@JsonIgnore
	@ManyToOne(optional=false)
	@JoinColumn(name="previous_question_log_id")
    private PreviousQuestionLog previousQuestionLog;
	
	
	public PlayerResponseLog() {
		super();
	}
	
	
	
	public PlayerResponseLog(Player player, User user, Option response,
			Long timeTakenToAnswer,  long questionId) {
		super();
		this.player = player;
		this.user = user;
		this.response = response;
		this.timeTakenToAnswer = timeTakenToAnswer;
		//this.questionId = questionId;
		markResponse(response);
	}



	public PlayerResponseLog(User user, Option response,
			Long timeTakenToAnswer, Integer rank) {
		super();
		this.user = user;
		this.response = response;
		this.timeTakenToAnswer = timeTakenToAnswer;
		this.rank = rank;
		
		markResponse(response);
	}
	
	private void markResponse(Option response){
		if(response != null && response.getId() == -1)
			this.response = null;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public PreviousQuestionLog fetchAssociatedPreviousQuestionLog() {
		return previousQuestionLog;
	}
	public void setPreviousQuestionLog(PreviousQuestionLog previousQuestionLog) {
		this.previousQuestionLog = previousQuestionLog;
	}
	public Option getResponse() {
		return response;
	}
	public void setResponse(Option response) {
		this.response = response;
		markResponse(response);
	}
	
	
	public Integer getRank() {
		return rank;
	}
	public Long getTimeTakenToAnswer() {
		return timeTakenToAnswer;
	}
	public void setTimeTakenToAnswer(Long timeTakenToAnswer) {
		this.timeTakenToAnswer = timeTakenToAnswer;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	
	public int compareTo(PlayerResponseLog prl){
		if(this.getTimeTakenToAnswer() > prl.getTimeTakenToAnswer()){
			return 1;
		}else if(this.getTimeTakenToAnswer() < prl.getTimeTakenToAnswer()){
			return -1;
		}else{
			return 0;
		}
	}



//	public long getQuestionId() {
//		return questionId;
//	}



	public void setQuestionId(long questionId) {
		//this.questionId = questionId;
	}



	public boolean isResponseCorrect() {
		return responseCorrect;
	}



	public void setResponseCorrect(boolean responseCorrect) {
		this.responseCorrect = responseCorrect;
	}



	public boolean isQuestionWinner() {
		return questionWinner;
	}



	public void setQuestionWinner(boolean questionWinner) {
		this.questionWinner = questionWinner;
	}
	
	
	

}
