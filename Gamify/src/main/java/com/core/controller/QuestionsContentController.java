package com.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.core.constants.EntityStateENUM;
import com.core.constants.GameConstants;
import com.core.domain.Question;
import com.core.domain.User;
import com.core.domain.lms.Topic;
import com.core.service.QuestionService;
import com.core.service.TopicService;


@Controller
@RequestMapping(value="/content/questions")
public class QuestionsContentController {
	
		
	@Autowired
	private TopicService topicService;
	

	@Autowired
	private QuestionService questionService;
	
	private Validator validator;
	
	@Autowired
	public QuestionsContentController(Validator validator) {
		this.validator = validator;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView show( @RequestParam("topicId") Long topicId, Model model, HttpServletRequest request) {
		String success = "";
		String error = "";
		model.addAttribute("error",error);
		return postRequestProcessing(topicId, error, success, request, model);
		
	}
	
	private ModelAndView postRequestProcessing(Long topicId, String error, String success, HttpServletRequest request, Model model){
		model.addAttribute("error",error);	
		model.addAttribute("success",success);	
		model.addAttribute("user",(User)request.getSession().getAttribute(GameConstants.SESSION_VARIABLE_LOGGEDIN_USER)); 
		Topic topic = topicService.findById(topicId);
		List<Question> 	questions = questionService.getQuestions(topic);		
		model.addAttribute("questions", questions);	
		model.addAttribute("topicId",topicId);
		model.addAttribute("examSectionName", topic.fetchExamSection().getName());
		model.addAttribute("examName", topic.fetchExamSection().fetchExam().getExamName());
		model.addAttribute("topicName",topic.getName());
		
		String htmlEscapedJson="{" +
				  "\"timestamp\":  1345719094," + 
				  "\"callback\": \"http://localhost/Gamify/content/questions?topicId=1\"," +
				  "\"signature\": \"WzRvYWdcA8qyagdkzjEIr9iJ0p4\", " +
				 "\"api_key\": \"465561835822868\", " +
				
				 "\"public_id\": \"previdus\","+
				"}";
		htmlEscapedJson = StringEscapeUtils.escapeHtml(htmlEscapedJson);
		model.addAttribute("htmlEscapedJson",htmlEscapedJson);
		
		return new ModelAndView("contentAdmin/questions");	
	}
	
	@RequestMapping(value="/addQuestion", method=RequestMethod.POST)
	public ModelAndView addQuestion(@RequestParam("topicId") long topicId, 
			@RequestParam("addQuestion") String questionText, 
			@RequestParam("difficultyLevel") byte difficultyLevel,
			Model model, 
			HttpServletRequest request) {
		
		Question question = null;
		String success="";
		String error="";		
		if(StringUtils.isEmpty(questionText)){
			error="question name cannot be empty";			
		}
		else if(difficultyLevel == 0){
			error="question should have a level chosen";
		}
		else{
			List<Question> questions = questionService.findByTextAndTopic(topicId, questionText);
			if(questions!= null){
				error="You cannot add a duplicate question";
			}
			else{
				
				try{
				  question = questionService.addQuestion(topicId, difficultyLevel, null, questionText);
				   success = "question successfully added";
				}
				catch(Exception e){
					error="internal error while adding question:"+e.getMessage();
				}
			}			
		}
		
		return postRequestProcessing(topicId, error,success,request,model);
	}
	
	
	@RequestMapping(value="/editQuestion", method=RequestMethod.POST)	
	public ModelAndView editQuestion(@RequestParam("topicId") Long topicId,@RequestParam("questionId") Long questionId, 
			@RequestParam("editQuestion") String newQuestionText , Model model, HttpServletRequest request) {
				
		String error = "";
		String success = "";
		if(StringUtils.isEmpty(newQuestionText)){			
			error = "question name cannot be empty";			
		}
		else{
			Question question = questionService.findById(questionId);
			if(question == null){
							
				error = "The question you are trying to edit doesn't exist in the system";
			}
			else if(question.getQuestionText().equals(newQuestionText)){
				
				error = "You cannot add a duplicate question";
			}
			else{
				
				List<Question> questions = questionService.findByTextAndTopic(topicId, newQuestionText);
				if(questions != null){
					error = "A question with this text already exists in the system. Please type  a different one";
				}
				else{
					try{
					   
					   questionService.editQuestionText(questionId, newQuestionText);
					   success = "question text successfully edited";
					}
					catch(Exception e){
						error ="internal error while editing question:"+e.getMessage();
					}
				}
				
			}			
		}		
		return postRequestProcessing(topicId, error,success,request,model);
		
	}
	@RequestMapping(value="/editQuestionLevel", method=RequestMethod.POST)	
	public ModelAndView editQuestionLevel(@RequestParam("topicId") Long topicId,@RequestParam("questionId") Long questionId,@RequestParam("difficultyLevel") byte level, Model model, HttpServletRequest request) {
				
		String error = "";
		String success = "";
		
		Question question = questionService.findById(questionId);
		if(question == null){
			error = "The question you are trying to change the difficulty level on doesn't exist in the system";
		}
		
		else{
			try{
				
				questionService.editQuestionDifficultyLevel(questionId, level);
				success = "question level successfully changed";
			}
			catch(Exception e){
				error ="internal error while editing question level:"+e.getMessage();
			}
		}			
		
		return postRequestProcessing(topicId, error,success,request,model);
		
	}
	
	/*@RequestMapping(value="/uploadImage", method=RequestMethod.POST)	
	public ModelAndView uploadImageForQuestion(@RequestParam("topicId") Long topicId,@RequestParam("questionId") Long questionId,
			@RequestParam("image") MultipartFile image, Model model, HttpServletRequest request) {
				
		String error = "";
		String success = "";
		
		Question question = questionService.findById(questionId);
		if(question == null){
			error = "The question you are trying to upload the image for doesn't exist in the system";
		}
		
		else{
			try{
				
				byte[] reducedQualityImage = GenericUtil.compress(image.getBytes());
				questionService.updateQuestionImage(questionId, reducedQualityImage);
				success = "question image successfully uploaded";
			}
			catch(Exception e){
				error ="internal error while uploading image for the question :"+e.getMessage();
			}
		}			
		
		return postRequestProcessing(topicId, error,success,request,model);
		
	}*/
	
	
	@RequestMapping(value="/disableQuestion", method=RequestMethod.GET)	
	public ModelAndView disableQuestion(@RequestParam("topicId") Long topicId, @RequestParam("questionId") Long questionId,Model model, HttpServletRequest request) {
				
	     return toggleQuestionState(topicId, questionId, model, request, EntityStateENUM.INACTIVE.name());
		
	}
	
	@RequestMapping(value="/enableQuestion", method=RequestMethod.GET)	
	public ModelAndView enableQuestion(@RequestParam("topicId") Long topicId, @RequestParam("questionId") Long questionId, Model model, HttpServletRequest request) {
				
	     return toggleQuestionState(topicId, questionId, model, request, EntityStateENUM.ACTIVE.name());
		
	}
	
	private ModelAndView toggleQuestionState(Long topicId,Long questionId, Model model,HttpServletRequest request, String state){
		String error = "";
		String success = "";
	    Question question = questionService.findById(questionId);
		if(question == null){
						
			error = "The question you are trying to disable doesn't exist in the system";
		}
		else{
			
			question.setState(state);
			try{
				   
			   questionService.saveQuestion(question);
			   success = "question state successfully changed";
			}
			catch(Exception e){
				error ="internal error while toggling  question state:"+e.getMessage();
			}
			
			
		}			
	
		return postRequestProcessing(topicId, error,success,request,model);
	}
	
	
	
	
	
	
	
}

