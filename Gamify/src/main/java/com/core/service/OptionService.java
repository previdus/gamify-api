package com.core.service;

import java.util.List;

import com.core.domain.Option;
import com.core.domain.Question;
import com.core.domain.lms.Exam;
import com.core.domain.lms.ExamSection;
import com.core.domain.lms.Topic;

public interface OptionService {
	
	public List<Option> showOptions(Long questionId);
	
	public Option addOption(Long questionId, String imageUrl, int ordr, String text);
	
	public Option editOption(Long optionId, String imageUrl, int ordr, String text);
		
	public void deleteOption(Long optionId); 

}
