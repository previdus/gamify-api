package com.core.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.ApiResult;
import com.core.constants.EntityStateENUM;
import com.core.domain.AnswerKey;
import com.core.service.AnswerKeyService;
import com.core.service.QuestionService;

@Controller
@RequestMapping(value = "/api/content/question/update")
public class ApiUpdateQuestionController {

	EntityStateENUM newQuestionAdditionState = EntityStateENUM.REVIEW_PENDING;

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private AnswerKeyService answerKeyService;
	
	
	@RequestMapping(value = "/answer", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult updateAnswerKey(
			@RequestParam(value = "questionId", required = false) Long questionId,
			@RequestParam(value = "correctOptionId", required = false) Long correctOptionId,
			Model model, HttpServletRequest request) {
		String success = null;
		String error = null;
		// validate inputs
		
		 if (questionId == null || questionId == 0)
			error = "Please tell which question you want to update!";
		 else if (correctOptionId == null || correctOptionId == 0)
			 error = "You have not specified a valid Option!";

		ApiResult result = new ApiResult();
		if (error == null) {
			answerKeyService.saveOrUpdateAnswerKey(questionId, correctOptionId);
			success = "Successfully Updated Answer Key for the question Question ...";
			result.setStatus(1);
			result.setMessage(success);
		} else {
			result.setStatus(-1);
			result.setMessage(error);
		}
		return result;
	}

	@RequestMapping(value = "/text", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult updateQuestionText(
			@RequestParam(value = "questionId", required = false) Long questionId,
			@RequestParam(value = "questionText", required = false) String questionText,
			Model model, HttpServletRequest request) {
		String success = null;
		String error = null;
		// validate inputs
		if (StringUtils.isEmpty(questionText))
			error = "Question name cannot be empty!";
		else if (questionId == null || questionId == 0)
			error = "Please tell which question you want to update!";

		ApiResult result = new ApiResult();
		if (error == null) {
			questionService.updateQuestionText(questionId, questionText);
			success = "Successfully Updated the Question...";
			result.setStatus(1);
			result.setMessage(success);
		} else {
			result.setStatus(-1);
			result.setMessage(error);
		}
		return result;
	}

	@RequestMapping(value = "/time", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult updateQuestionTime(
			@RequestParam(value = "questionId", required = false) Long questionId,
			@RequestParam(value = "timeAllocated", required = false) Integer timeAllocated,
			Model model, HttpServletRequest request) {
		String success = null;
		String error = null;
		// validate inputs
		if (timeAllocated == null || timeAllocated == 0)
			error = "Question should have time allocated!";
		else if (questionId == null || questionId == 0)
			error = "Please tell which question you want to update!";

		ApiResult result = new ApiResult();
		if (error == null) {
			questionService.updateQuestionTimeAllocation(questionId,
					timeAllocated);
			success = "Successfully Updated the Question...";
			result.setStatus(1);
			result.setMessage(success);
		} else {
			result.setStatus(-1);
			result.setMessage(error);
		}
		return result;
	}

	@RequestMapping(value = "/difficultyLevel", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult updateDifficultyLevel(
			@RequestParam(value = "questionId", required = false) Long questionId,
			@RequestParam(value = "difficultyLevel", required = false) Byte difficultyLevel,
			Model model, HttpServletRequest request) {
		String success = null;
		String error = null;
		// validate inputs
		if (difficultyLevel == null || difficultyLevel == 0)
			error = "Question should have a Difficulty level assigned!";
		else if (questionId == null || questionId == 0)
			error = "Please tell which question you want to update!";

		ApiResult result = new ApiResult();
		if (error == null) {
			questionService.editQuestionDifficultyLevel(questionId,
					difficultyLevel);
			success = "Successfully Updated the Question...";
			result.setStatus(1);
			result.setMessage(success);
		} else {
			result.setStatus(-1);
			result.setMessage(error);
		}
		return result;
	}

	@RequestMapping(value = "/enable", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult enable(
			@RequestParam(value = "questionId", required = false) Long questionId,
			Model model, HttpServletRequest request) {
		String success = null;
		String error = null;
		// validate inputs
		if (questionId == null || questionId == 0)
			error = "Please tell which question you want to update!";

		ApiResult result = new ApiResult();
		if (error == null) {
			questionService.enableQuestion(questionId);
			success = "Successfully enabled Question...";
			result.setStatus(1);
			result.setMessage(success);
		} else {
			result.setStatus(-1);
			result.setMessage(error);
		}
		return result;
	}

	@RequestMapping(value = "/disable", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult disable(
			@RequestParam(value = "questionId", required = false) Long questionId,
			Model model, HttpServletRequest request) {
		String success = null;
		String error = null;
		// validate inputs
		if (questionId == null || questionId == 0)
			error = "Please tell which question you want to update!";

		ApiResult result = new ApiResult();
		if (error == null) {
			questionService.disableQuestion(questionId);
			success = "Successfully disabled Question...";
			result.setStatus(1);
			result.setMessage(success);
		} else {
			result.setStatus(-1);
			result.setMessage(error);
		}
		return result;
	}

	@RequestMapping(value = "/softDelete", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult softDelete(
			@RequestParam(value = "questionId", required = false) Long questionId,
			Model model, HttpServletRequest request) {
		String success = null;
		String error = null;
		// validate inputs
		if (questionId == null || questionId == 0)
			error = "Please tell which question you want to update!";

		ApiResult result = new ApiResult();
		if (error == null) {
			questionService.softDeleteQuestion(questionId);
			success = "Successfully Soft Deleted Question...";
			result.setStatus(1);
			result.setMessage(success);
		} else {
			result.setStatus(-1);
			result.setMessage(error);
		}
		return result;
	}

}