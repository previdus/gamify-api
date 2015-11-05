package com.core.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.ApiResult;
import com.core.constants.GameConstants;
import com.core.constants.IncorrectReasonENUM;
import com.core.domain.User;
import com.core.service.SelfAnalysisToolService;

@Controller
@RequestMapping(value = "/api/tool/self-analysis")
public class ApiSelfAnalysisController {

	@Autowired
	private SelfAnalysisToolService selfAnalysisToolService;

	@RequestMapping(value = "/incorrect-reason", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult incorrectReason(
			@RequestParam(value = "gameId", required = true) Long gameId,
			@RequestParam(value = "questionId", required = true) Long questionId,
			@RequestParam(value = "reason", required = true) String reason,
			Model model, HttpServletRequest request) {

		ApiResult result = (ApiResult) request.getSession().getAttribute(
				GameConstants.SESSION_VARIABLE_LOGGEDIN_USER_RESULT);
		User loggedInUser = (User) request.getSession().getAttribute(
				GameConstants.SESSION_VARIABLE_LOGGEDIN_USER);

		Long userId = result.getUser() != null ? result.getUser().getId()
				: null;
		userId = (userId == null || userId == 0) && (loggedInUser != null) ? loggedInUser
				.getId() : null;
		if (userId != null && userId != 0) {
			IncorrectReasonENUM incorrectReason = IncorrectReasonENUM
					.valueOf(reason);
			selfAnalysisToolService.addReason(gameId, userId, questionId,
					incorrectReason);
		} else {
			result.setStatus(-1);
			result.setMessage("User not loggedIn");
		}
		return result;
	}

	@RequestMapping(value = "/star-mark", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult starMark(
			@RequestParam(value = "gameId", required = true) Long gameId,
			@RequestParam(value = "questionId", required = true) Long questionId,
			Model model, HttpServletRequest request) {

		ApiResult result = (ApiResult) request.getSession().getAttribute(
				GameConstants.SESSION_VARIABLE_LOGGEDIN_USER_RESULT);
		User loggedInUser = (User) request.getSession().getAttribute(
				GameConstants.SESSION_VARIABLE_LOGGEDIN_USER);

		Long userId = result.getUser() != null ? result.getUser().getId()
				: null;
		userId = (userId == null || userId == 0) && (loggedInUser != null) ? loggedInUser
				.getId() : null;
		if (userId != null && userId != 0) {
			selfAnalysisToolService.starMark(gameId, userId, questionId);
		} else {
			result.setStatus(-1);
			result.setMessage("User not loggedIn");
		}
		return result;
	}

	@RequestMapping(value = "/request-solution", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult requestSolution(
			@RequestParam(value = "gameId", required = true) Long gameId,
			@RequestParam(value = "questionId", required = true) Long questionId,
			Model model, HttpServletRequest request) {

		ApiResult result = (ApiResult) request.getSession().getAttribute(
				GameConstants.SESSION_VARIABLE_LOGGEDIN_USER_RESULT);
		User loggedInUser = (User) request.getSession().getAttribute(
				GameConstants.SESSION_VARIABLE_LOGGEDIN_USER);

		Long userId = result.getUser() != null ? result.getUser().getId()
				: null;
		userId = (userId == null || userId == 0) && (loggedInUser != null) ? loggedInUser
				.getId() : null;
		if (userId != null && userId != 0) {
			selfAnalysisToolService.requestSolution(gameId, userId, questionId);
		} else {
			result.setStatus(-1);
			result.setMessage("User not loggedIn");
		}
		return result;
	}

}
