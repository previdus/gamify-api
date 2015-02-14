package com.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="answer_key")
public class AnswerKey implements Serializable {
	
	
	    @Id
	    @Column(name="question_id", unique=true, nullable=false)
	    private Long questionId;
	
	    
	    @Column(name="option_id", unique=true, nullable=false)
	    private Long optionId;

	   /* @OneToOne
	    @PrimaryKeyJoinColumn*/
	    private transient Question question;
	    
	   /* @OneToOne
	    @PrimaryKeyJoinColumn*/
	    private transient Option answer;
	    
	    public AnswerKey(){
	    	super();
	    }
	    
	    

		public AnswerKey(Long questionId, Long optionId) {
			super();
			this.questionId = questionId;
			this.optionId = optionId;
		}



		public AnswerKey(Question question, Option answer) {
			super();
			this.question = question;
			this.answer = answer;
			this.questionId = question.getId();
			this.optionId = answer.getId();
		}

		/*public Question getQuestion() {
			return new Question(this.questionId);
		}
*/
		public void setQuestion(Question question) {
			this.questionId = question.getId();
		}

	/*	public Option getAnswer() {
			return new answer;
		}*/

		public void setAnswer(Option answer) {
			this.optionId = answer.getId();
		}



		public Long getQuestionId() {
			return questionId;
		}



		public Long getOptionId() {
			return optionId;
		}
		
		
		
}
