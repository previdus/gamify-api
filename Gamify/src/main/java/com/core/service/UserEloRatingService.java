package com.core.service;

import java.util.List;

import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.UserEloRatingPerExam;
import com.core.domain.knockout.UserEloRatingPerExamSection;
import com.core.domain.knockout.UserEloRatingPerTopic;

public interface UserEloRatingService {

	public void calulateUserEloRating(GameInstance gameInstance);
	public List<UserEloRatingPerTopic> getTopUserEloRatingsPerTopic(Integer noOfPlayersToShow, Long topicId, boolean excludeProvisional);
	public List<UserEloRatingPerExamSection> getTopUserEloRatingsPerExamSection(Integer noOfPlayersToShow, Long examSectionId, boolean excludeProvisional);
	public List<UserEloRatingPerExam> getTopUserEloRatingsPerExam(Integer noOfPlayersToShow, Long examId, boolean excludeProvisional);
	
	

}
