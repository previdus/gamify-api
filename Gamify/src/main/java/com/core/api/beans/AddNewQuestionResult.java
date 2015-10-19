package com.core.api.beans;

import com.core.domain.Question;

public class AddNewQuestionResult extends ApiResult{
	
	Question addedQuestion;
	
	public AddNewQuestionResult(Question addedQuestion) {
		this.addedQuestion = addedQuestion;
	}

}
