package com.core.service;

import java.util.List;

import com.core.api.beans.UserEloRatingDTO;
import com.core.domain.knockout.GameInstance;
import com.core.domain.knockout.UserEloRatingPerTopic;

public interface UserEloRatingService {

	public void calulateUserEloRating(GameInstance gameInstance);
	public List<UserEloRatingPerTopic> getTopUserEloRatingsPerTopic(Integer noOfPlayersToShow, Long topicId, boolean excludeProvisional);
	public List<UserEloRatingDTO> getTopUserEloRatingsPerExamSection(Integer noOfPlayersToShow, Long examSectionId);
	public List<UserEloRatingDTO> getTopUserEloRatingsPerExam(Integer noOfPlayersToShow, Long examId);
	
	

}
