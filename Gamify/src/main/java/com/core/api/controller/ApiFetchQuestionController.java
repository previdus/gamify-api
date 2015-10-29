package com.core.api.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.QuestionAnswerDTO;
import com.core.api.beans.SearchQuestionResult;
import com.core.constants.EntityStateENUM;
import com.core.domain.AnswerKey;
import com.core.domain.Option;
import com.core.domain.Question;
import com.core.service.AnswerKeyService;
import com.core.service.QuestionService;
import com.google.gson.Gson;


@Controller
@RequestMapping(value="/api/content/questions")
public class ApiFetchQuestionController {
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private AnswerKeyService answerKeyService;
	
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public SearchQuestionResult searchQuestion(
			@RequestParam(value = "questionId", required = false) Long questionId,
			Model model, HttpServletRequest request) 
	{
		Question question = null;
		AnswerKey answerKey = null;
		String success = null;
		String error = null;
		if(questionId == null || questionId == 0)
			error = "Question Id not provided";
		SearchQuestionResult result = new SearchQuestionResult();
		if(error == null){
			question = questionService.findById(questionId);
			//question.setOptions(null);
			//question.setTopic(null);
			if(question == null){
				error = "Question Not Found";
			}else{
				
			    answerKey = answerKeyService.getAnswerKey(question);
			}
			if(answerKey == null)
				error = "Answer Key not Found";
			makeItSerializable(question);
			result.setAnswerKey(answerKey);
			result.setQuestion(question);
		}
		if(error == null){
		success = "Successfully Found the Question...";
		result.setStatus(1);
		result.setMessage(success);
		}
		else{
			result.setStatus(-1);
			result.setMessage(error);
		}
		
		return result;
		
	}
	
	
	@RequestMapping(value = "/search-paginated", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String searchQuestion(
			@RequestParam(value = "topicId", required = false) Long topicId,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "limit", required = false) Integer limit,
			Model model, HttpServletRequest request) 
	{
		List<QuestionAnswerDTO> questionAnswerDTOs = new LinkedList<QuestionAnswerDTO>();
		List<Question> questions = questionService.findByTopicStatePageNo(topicId, EntityStateENUM.valueOf(state), pageNo, limit);
		Gson gson = new Gson();
		if(questions == null || questions.size() == 0){
			return gson.toJson( new SearchQuestionResult(questionAnswerDTOs, -1, "No Result Available!"));
		}
		else{
			for(Question question: questions){
				makeItSerializable(question);
				questionAnswerDTOs.add(new QuestionAnswerDTO(question, answerKeyService.getAnswerKey(question)));
			}
			
			
			SearchQuestionResult s =  new SearchQuestionResult(questionAnswerDTOs, 1, " Successfully fetched " + questionAnswerDTOs.size() + " Questions! ");
			//System.out.println("--------------------------------------------------------------------------------");
			//System.out.println(gson.toJson(s)); 
			return gson.toJson(s);
		}
		
	}
	
	private Question makeItSerializable(Question question){
		for(Option option : question.getOptions()){
			option.setQuestion(null);
		}
		question.setTopic(null);
		return question;
	}
	
	
	
}