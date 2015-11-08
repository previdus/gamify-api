package com.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.constants.EntityStateENUM;
import com.core.dao.OptionDAO;
import com.core.domain.Option;
import com.core.domain.Question;
import com.core.service.OptionService;
import com.core.service.QuestionService;

@Service("optionService")
public class OptionServiceImpl implements OptionService {

	@Autowired
	private OptionDAO optionDAO;
	
	@Autowired
	private QuestionService questionService;

	public List<Option> getOptions(Question question) {
		return optionDAO.getOptions(question);
	}

	public Option saveOption(Option option){
		return optionDAO.saveOrUpdate(option);
	}
	
	public Option findById(long optionId){
		return optionDAO.findObjectById(optionId);
	}
	
	
	public Option addOption(long questionId,  String imageUrl, String optionText, int order){
		Option option = new Option();
		Question question = questionService.findById(questionId);
		option.setQuestion(question);
		option.setImageUrl(imageUrl);
		option.setState(EntityStateENUM.INACTIVE);
		option.setOrdr(order);
		option.setText(optionText);
		return optionDAO.saveOrUpdate(option);
	}
	public Option editOptionText(long optionId, String optionText){
		Option option = findById(optionId);	
		option.setText(optionText);
		return optionDAO.saveOrUpdate(option);
		
	}
	public Option updateOptionImage(long optionId, String imageUrl){
		Option option = findById(optionId);	
		option.setImageUrl(imageUrl);
		return optionDAO.saveOrUpdate(option);
		
	}
	public Option editOptionOrdr(long optionId, int order){
		Option option = findById(optionId);	
		option.setOrdr(order);
		return optionDAO.saveOrUpdate(option);
		
	}
	public List<Option> findByOptionTextAndQuestion(long questionId, String optionText){
		return optionDAO.findByOptionTextAndQuestion(questionId, optionText);
	}
	
	public  List<Option> findActiveOptionByOrderAndQuestion(long questionId, int order){
		return optionDAO.findActiveOptionByOrderAndQuestion(questionId, order);
	}

	public Option updateOptionText(Long optionId, String optionText) {
		Option option = findById(optionId);
		option.setText(optionText);
		return optionDAO.saveOrUpdate(option);
		
	}

	public Option enableOption(Long optionId) {
		Option option = findById(optionId);
		option.setState(EntityStateENUM.ACTIVE);
		return optionDAO.saveOrUpdate(option);
	}

	public Option disableQuestion(Long optionId) {
		Option option = findById(optionId);
		option.setState(EntityStateENUM.INACTIVE);
		return optionDAO.saveOrUpdate(option);
	}

	public Option softDelete(Long optionId) {
		Option option = findById(optionId);
		option.setState(EntityStateENUM.SOFT_DELETE);
		return optionDAO.saveOrUpdate(option);
	}

	public  List<Option> findFreeResponseOptionByQuestionId(long questionId){
		return optionDAO.findFreeResponseOptionByQuestionId(questionId);
	}
}
