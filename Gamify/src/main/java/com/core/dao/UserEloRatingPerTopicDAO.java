package com.core.dao;

import java.io.Serializable;
import java.util.List;

import com.core.dao.generic.GenericRepository;
import com.core.domain.knockout.UserEloRatingPerExam;
import com.core.domain.knockout.UserEloRatingPerExamSection;
import com.core.domain.knockout.UserEloRatingPerTopic;

public interface UserEloRatingPerTopicDAO extends GenericRepository<UserEloRatingPerTopic, Serializable> {
	
	public UserEloRatingPerTopic getUserEloRatingPerTopic(Long userId, Long topicId);
	
	public List<UserEloRatingPerTopic> getTopUserEloRatingsPerTopic(Integer noOfPlayersToShow, Long topicId, boolean excludeProvisional);
	
    

}
