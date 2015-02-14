package com.core.dao;

import java.io.Serializable;

import javassist.bytecode.analysis.Analyzer;

import com.core.dao.generic.GenericRepository;
import com.core.domain.AnswerKey;
import com.core.domain.Option;
import com.core.domain.Question;

public interface AnswerKeyDAO extends GenericRepository<AnswerKey, Serializable> {

	public boolean isCorrectAnswer(Long questionId,Option answer);
	
	public AnswerKey getAnswerKey(Question question);
	
}
