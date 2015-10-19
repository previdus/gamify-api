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
import com.core.domain.Option;
import com.core.service.OptionService;
import com.core.service.QuestionService;

@Controller
@RequestMapping(value = "/api/content/option/update")
public class ApiUpdateOptionController {

	EntityStateENUM newoptionAdditionState = EntityStateENUM.REVIEW_PENDING;

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private OptionService optionService;
	
	@RequestMapping(value = "/addNew", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult addNewOption(
			@RequestParam(value = "questionId", required = false) Long questionId,
			@RequestParam(value = "optionText", required = false) String optionText,
			Model model, HttpServletRequest request) {
		String success = null;
		String error = null;
		// validate inputs
		if (StringUtils.isEmpty(optionText))
			error = "Option text cannot be empty!";
		else if (questionId == null || questionId == 0)
			error = "Please tell for which question you want to update the option!";

		ApiResult result = new ApiResult();
		if (error == null) {
			Option option = questionService.addNewOption(questionId, optionText);
			success = "Successfully Added the Option ...";
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
	public ApiResult updateoptionText(
			@RequestParam(value = "questionId", required = false) Long questionId,
			@RequestParam(value = "optionId", required = false) Long optionId,
			@RequestParam(value = "optionText", required = false) String optionText,
			Model model, HttpServletRequest request) {
		String success = null;
		String error = null;
		
		// validate inputs
		if (StringUtils.isEmpty(optionText))
			error = "Option text cannot be empty!";
		if (optionId == null || optionId == 0)
			return addNewOption(questionId, optionText, model, request);

		ApiResult result = new ApiResult();
		if (error == null) {
			optionService.updateOptionText(optionId, optionText);
			success = "Successfully Updated the Option text...";
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
			@RequestParam(value = "optionId", required = false) Long optionId,
			Model model, HttpServletRequest request) {
		String success = null;
		String error = null;
		// validate inputs
		if (optionId == null || optionId == 0)
			error = "Please tell which option you want to update!";

		ApiResult result = new ApiResult();
		if (error == null) {
			Option option = optionService.enableOption(optionId);
			success = "Successfully enabled option...";
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
			@RequestParam(value = "optionId", required = false) Long optionId,
			Model model, HttpServletRequest request) {
		String success = null;
		String error = null;
		// validate inputs
		if (optionId == null || optionId == 0)
			error = "Please tell which option you want to update!";

		ApiResult result = new ApiResult();
		if (error == null) {
			Option option = optionService.disableQuestion(optionId);
			success = "Successfully disabled Option...";
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
			@RequestParam(value = "optionId", required = false) Long optionId,
			Model model, HttpServletRequest request) {
		String success = null;
		String error = null;
		// validate inputs
		if (optionId == null || optionId == 0)
			error = "Please tell which option you want to update!";

		ApiResult result = new ApiResult();
		if (error == null) {
			Option option = optionService.softDelete(optionId);
			success = "Successfully Soft Deleted option...";
			result.setStatus(1);
			result.setMessage(success);
		} else {
			result.setStatus(-1);
			result.setMessage(error);
		}
		return result;
	}

}