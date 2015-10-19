package com.core.api.beans;

import java.util.List;

import com.core.domain.AnswerKey;
import com.core.domain.Question;

public class SearchQuestionResult extends ApiResult{
	
	Question question;
	
	AnswerKey answerKey;

	List<QuestionAnswerDTO> questions; 
	
	public SearchQuestionResult() {
	}
	
	public SearchQuestionResult(Question question, AnswerKey answerKey, int status, String message) {
		this.question = question;
		this.answerKey = answerKey;
		super.setMessage(message);
		super.setStatus(status);
	}
	
	public SearchQuestionResult(List<QuestionAnswerDTO> questions, int status, String message) {
		this.questions = questions;
		super.setMessage(message);
		super.setStatus(status);
	}

	

	public List<QuestionAnswerDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionAnswerDTO> questions) {
		this.questions = questions;
	}

	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question quest) {
		this.question = quest;
	}
	public AnswerKey getAnswerKey() {
		return answerKey;
	}
	public void setAnswerKey(AnswerKey answerKey) {
		this.answerKey = answerKey;
	}
	
}
