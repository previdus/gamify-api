package com.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.constants.IncorrectReasonENUM;
import com.core.dao.IncorrectReasonToolDAO;
import com.core.dao.RequestSolutionDAO;
import com.core.dao.StarMarkQuestionDAO;
import com.core.domain.Question;
import com.core.domain.knockout.IncorrectReasonTool;
import com.core.domain.knockout.RequestSolution;
import com.core.domain.knockout.StarMarkQuestion;
import com.core.service.SelfAnalysisToolService;

@Service("selfAnalysisToolService")
public class SelfAnalysisToolServiceImpl implements SelfAnalysisToolService {

	@Autowired
	private IncorrectReasonToolDAO incorrectReasonToolDAO;

	@Autowired
	private StarMarkQuestionDAO starMarkQuestionDAO;

	@Autowired
	private RequestSolutionDAO requestSolutionDAO;

	public void addReason(long gameId, long userId, long questionId,
			IncorrectReasonENUM reason) {

		IncorrectReasonTool reasonTool = incorrectReasonToolDAO.get(gameId,
				userId, questionId);
		if (reasonTool == null)
			reasonTool = new IncorrectReasonTool(new Question(questionId),
					userId, gameId, reason);
		reasonTool.setReason(reason);
		incorrectReasonToolDAO.saveOrUpdate(reasonTool);
	}

	public void starMark(Long gameId, Long userId, Long questionId) {

		StarMarkQuestion starMarkQuestion = starMarkQuestionDAO.get(gameId,
				userId, questionId);
		if (starMarkQuestion == null) {
			starMarkQuestion = new StarMarkQuestion(questionId, userId, gameId);
			starMarkQuestionDAO.saveNew(starMarkQuestion);
		}

	}

	public void requestSolution(Long gameId, Long userId, Long questionId) {
		RequestSolution requestSolution = requestSolutionDAO.get(gameId,
				userId, questionId);
		if (requestSolution == null) {
			requestSolution = new RequestSolution(questionId, userId, gameId);
			requestSolutionDAO.saveNew(requestSolution);
		}

	}

}
