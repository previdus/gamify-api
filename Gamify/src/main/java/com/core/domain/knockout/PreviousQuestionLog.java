package com.core.domain.knockout;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.core.domain.AnswerKey;
import com.core.domain.Question;
import com.core.domain.User;

@Entity
@Table(name="previous_question_log")
public class PreviousQuestionLog  implements Serializable{
	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="question_id")
	private Question question;
	
	@OneToMany(mappedBy="previousQuestionLog" ,cascade = { javax.persistence.CascadeType.ALL })
	private List< PlayerResponseLog> playersResponses;
	
	@Column(name="best_time")
	private String bestTime;
	
	@ManyToOne(optional=true)
	@JoinColumn(name="winner_user_id")
	private User winner;
	
	//private transient AnswerKey answerKey;
	
	public GameInstance fetchAssociatedGameInstance() {
		return gameInstance;
	}
	public void setGameInstance(GameInstance gameInstance) {
		this.gameInstance = gameInstance;
	}
	
	@JsonIgnore
	@ManyToOne(optional=false)
	@JoinColumn(name="game_instance_id")
	private GameInstance gameInstance;
	
	
	
	
	public PreviousQuestionLog() {
		super();
	}
	public PreviousQuestionLog( Question question, String bestTime,
			User winner, 
			//AnswerKey answerKey, 
			GameInstance gameInstance) {
		super();
		this.question = question;
		this.playersResponses = new LinkedList<PlayerResponseLog>();
		this.bestTime = bestTime;
		this.winner = winner;
		//this.answerKey = answerKey;
		this.gameInstance = gameInstance;
	}
	
	public void addPlayerResponses(PlayerResponseLog playerResponseLog){
		playerResponseLog.setPreviousQuestionLog(this);
		playersResponses.add(playerResponseLog);
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public List<PlayerResponseLog> getPlayersResponses() {
		return playersResponses;
	}
	public void setPlayersResponses(List<PlayerResponseLog> playersResponses) {
		for(PlayerResponseLog prl:playersResponses){
			prl.setPreviousQuestionLog(this);
		}
		this.playersResponses = playersResponses;
	}
	public String getBestTime() {
		return bestTime;
	}
	public void setBestTime(String bestTime) {
		this.bestTime = bestTime;
	}
	public User getWinner() {
		return winner;
	}
	public void setWinner(User winner) {
		this.winner = winner;
	}
	/*public AnswerKey getAnswerKey() {
		return answerKey;
	}
	public void setAnswerKey(AnswerKey answerKey) {
		this.answerKey = answerKey;
	}*/
	
	
	
	
			
}
