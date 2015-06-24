package com.core.dao;

import java.io.Serializable;
import java.util.List;

import com.core.dao.generic.GenericRepository;
import com.core.domain.Option;
import com.core.domain.Question;

public interface OptionDAO extends GenericRepository<Option, Serializable> {
	public List<Option> getOptions(Question question);

	public Option getOption(Question question);
	
	public  List<Option> findByOptionTextAndQuestion(long questionId, String optionText);
	public  List<Option> findActiveOptionByOrderAndQuestion(long questionId, int order);
}
