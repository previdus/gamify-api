package com.core.service;

import java.util.List;

import com.core.domain.Option;
import com.core.domain.Question;


public interface OptionService {
	
	public List<Option> getOptions(Question question);
	public Option findById(long optionId);
	public Option saveOption(Option option);
	public Option addOption(long questionId,  String imageUrl, String optionText, int order);
	public Option editOptionText(long optionId, String optionText);
	public Option updateOptionImage(long optionId, String imageUrl);
	public Option editOptionOrdr(long optionId, int order);	
	public List<Option> findByOptionTextAndQuestion(long questionId, String optionName);
	public  List<Option> findActiveOptionByOrderAndQuestion(long questionId, int order);
	public Option updateOptionText(Long optionId, String optionText);
	public Option enableOption(Long optionId);
	public Option disableQuestion(Long optionId);
	public Option softDelete(Long optionId);

}
