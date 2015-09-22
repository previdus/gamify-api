package com.core.dao;

import java.io.Serializable;
import java.util.List;

import com.core.dao.generic.GenericRepository;
import com.core.domain.knockout.UserEloRatingPerExam;

public interface UserEloRatingPerExamDAO extends GenericRepository<UserEloRatingPerExam, Serializable> {
	
	
	
    public UserEloRatingPerExam getUserEloRatingPerExam(Long userId, Long examId);
	
	public List<UserEloRatingPerExam> getTopUserEloRatingsPerExam(Integer noOfPlayersToShow, Long examId, boolean excludeProvisional);
	
	

}
