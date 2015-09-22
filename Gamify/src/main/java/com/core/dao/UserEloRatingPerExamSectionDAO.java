package com.core.dao;

import java.io.Serializable;
import java.util.List;

import com.core.dao.generic.GenericRepository;
import com.core.domain.knockout.UserEloRatingPerExamSection;

public interface UserEloRatingPerExamSectionDAO extends GenericRepository<UserEloRatingPerExamSection, Serializable> {
	
	
	
    public UserEloRatingPerExamSection getUserEloRatingPerExamSection(Long userId, Long examSectionId);
	
	public List<UserEloRatingPerExamSection> getTopUserEloRatingsPerExamSection(Integer noOfPlayersToShow, Long examSectionId, boolean excludeProvisional);
	
	

}
