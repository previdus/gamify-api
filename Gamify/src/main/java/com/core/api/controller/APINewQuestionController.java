package com.core.api.controller;

import java.util.LinkedList;
import java.util.List;

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
import com.core.domain.Question;
import com.core.domain.lms.Topic;
import com.core.service.QuestionService;

@Controller
@RequestMapping(value = "/api/content/questions/add")
public class APINewQuestionController {

	EntityStateENUM newQuestionAdditionState = EntityStateENUM.REVIEW_PENDING;
	
	@Autowired
	private QuestionService questionService;

	@RequestMapping(value = "/MCQ", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult addNewMcqQuestion(
			@RequestParam(value = "topicId", required = false) Long topicId,
			@RequestParam(value = "questionText", required = false) String questionText,
			@RequestParam(value = "difficultyLevel", required = false) Byte difficultyLevel,
			@RequestParam(value = "maxTimeAllocatedInSecs", required = false) Integer maxTimeAllocatedInSecs,
			@RequestParam(value = "option1Text", required = false) String option1Text,
			@RequestParam(value = "option2Text", required = false) String option2Text,
			@RequestParam(value = "option3Text", required = false) String option3Text,
			@RequestParam(value = "option4Text", required = false) String option4Text,
			@RequestParam(value = "option5Text", required = false) String option5Text,
			@RequestParam(value = "correctOptionNumber", required = false) Integer correctOptionNumber,
			@RequestParam(value = "image_url", required = false, defaultValue = "") String imageUploadUrl,
			Model model, HttpServletRequest request) {
		Question question = null;
		String success = null;
		String error = null;
		ApiResult result = new ApiResult();
		if (StringUtils.isEmpty(questionText)) {
			error = "Question name cannot be empty!";
		} else if (difficultyLevel == null || difficultyLevel == 0) {
			error = "Question should have a level chosen!";
		} else if (topicId == null || topicId == 0) {
			error = "It should be attached to topic!";
		} else if (maxTimeAllocatedInSecs == null
				|| maxTimeAllocatedInSecs <= 0) {
			error = "Time not allocated for question!";
		} else if (correctOptionNumber == null || correctOptionNumber <= 0
				|| correctOptionNumber > 5)
			error = "Correct option not provided!";
		Option correctOption =  null;
		if (correctOptionNumber != null) {
			switch (correctOptionNumber) {
			case 1:
				if (StringUtils.isEmpty(option1Text))
					error = "Correct option provided is left empty!";
				else{
					correctOption = new Option(option1Text, null, 1 , question, newQuestionAdditionState);
					option1Text = null;
				}
				break;
			case 2:
				if (StringUtils.isEmpty(option2Text))
					error = "Correct option provided is left empty!";
				else{
					correctOption = new Option(option2Text, null, 1 , question, newQuestionAdditionState);
					option2Text = null;
				}
				break;
			case 3:
				if (StringUtils.isEmpty(option3Text))
					error = "Correct option provided is left empty!";
				else{
					correctOption = new Option(option3Text, null, 1 , question, newQuestionAdditionState);
					option3Text = null;
				}
				break;
			case 4:
				if (StringUtils.isEmpty(option4Text))
					error = "Correct option provided is left empty!";
				else{
					correctOption = new Option(option4Text, null, 1 , question, newQuestionAdditionState);
					option4Text = null;
				}
				break;
			case 5:
				if (StringUtils.isEmpty(option5Text))
					error = "Correct option provided is left empty!";
				else{
					correctOption = new Option(option5Text, null, 1 , question, newQuestionAdditionState);
					option5Text = null;
				}
				break;
			default:
				error = "Please mark the correct option!";
				break;
			}
		}
		if(error != null){
			result.setStatus(-1);
			result.setMessage(error);
			return result;
		}
		int order = 1;
		question = new Question(questionText, imageUploadUrl,
				maxTimeAllocatedInSecs, new Topic(topicId), difficultyLevel,
				newQuestionAdditionState);
		List<Option> options = new LinkedList<Option>();
		if (!StringUtils.isEmpty(option1Text))
			options.add(new Option(option1Text, null, order++, question,newQuestionAdditionState));
		if (!StringUtils.isEmpty(option2Text))
			options.add(new Option(option2Text, null, order++, question,newQuestionAdditionState));
		if (!StringUtils.isEmpty(option3Text))
			options.add(new Option(option3Text, null, order++, question,newQuestionAdditionState));
		if (!StringUtils.isEmpty(option4Text))
			options.add(new Option(option4Text, null, order++, question,newQuestionAdditionState));
		if (!StringUtils.isEmpty(option5Text))
			options.add(new Option(option5Text, null, order++, question,newQuestionAdditionState));
		if (options.size() < 1) {
			error = "There should be atleast two options for this question!";
		}
		
		if (error == null) {
			question.setOptions(options);
			questionService.saveMCQQuestion(question,options, correctOption);
			success = "Successfully Added the Question...";
			result.setStatus(1);
			result.setMessage(success);
		} else {
			result.setStatus(-1);
			result.setMessage(error);
		}
		return result;
	}

	@RequestMapping(value = "/freeText", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult addNewFreeTextQuestion(
			@RequestParam(value = "topicId", required = false) Long topicId,
			@RequestParam(value = "questionText", required = false) String questionText,
			@RequestParam(value = "correctAnswer", required = false) String correctAnswer,
			@RequestParam(value = "difficultyLevel", required = false) Byte difficultyLevel,
			@RequestParam(value = "maxTimeAllocatedInSecs", required = false) Integer maxTimeAllocatedInSecs,
			@RequestParam(value = "image_url", defaultValue = "") String imageUploadUrl,
			Model model, HttpServletRequest request) {
		Question question = null;
		String success = null;
		String error = null;
		
		 if (StringUtils.isEmpty(questionText)) {
			error = "Question name cannot be empty!";
		} else if (difficultyLevel == null || difficultyLevel == 0) {
			error = "Question should have a level chosen!";
		} else if (topicId == null || topicId == 0) {
			error = "It should be attached to topic!";
		} else if (maxTimeAllocatedInSecs == null || maxTimeAllocatedInSecs <= 0) {
			error = "Time not allocated for question!";
		} else if (StringUtils.isEmpty(correctAnswer))
			error = "Correct Answer not provided!";

		ApiResult result = new ApiResult();
		if (error == null) {
			question = new Question(questionText, imageUploadUrl,
					maxTimeAllocatedInSecs, new Topic(topicId),
					difficultyLevel, newQuestionAdditionState);
			Option correctOption = new Option(correctAnswer, null, 1, question, newQuestionAdditionState);
			questionService.saveFreeTextQuestion(question, correctOption);
			success = "Successfully Added the Question...";
			result.setStatus(1);
			result.setMessage(success);
		} else {
			result.setStatus(-1);
			result.setMessage(error);
		}
		return result;
	}
}
