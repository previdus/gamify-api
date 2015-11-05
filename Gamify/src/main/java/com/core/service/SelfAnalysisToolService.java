package com.core.service;

import com.core.constants.IncorrectReasonENUM;

public interface SelfAnalysisToolService {
	
	void addReason(long gameId, long userId, long questionId, IncorrectReasonENUM reason);

	void starMark(Long gameId, Long userId, Long questionId);
	
	void requestSolution(Long gameId, Long userId, Long questionId);
	

}
