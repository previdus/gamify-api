package com.admin.controller;

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

import com.core.constants.CDNConstants;
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
		List<Question> 	questions = questionService.getAllQuestions(topic);		
		model.addAttribute("questions", questions);	
		model.addAttribute("topicId",topicId);
		model.addAttribute("examSectionName", topic.fetchExamSection().getName());
		model.addAttribute("examName", topic.fetchExamSection().fetchExam().getExamName());
		model.addAttribute("topicName",topic.getName());		
		model.addAttribute("htmlEscapedJson",CDNConstants.getRequiredEscapedHtmlJsonStringForCloudinaryImageUpload());		
		return new ModelAndView("contentAdmin/questions");	
	}
	
	@RequestMapping(value="/addQuestion", method=RequestMethod.POST)
	public ModelAndView addQuestion(@RequestParam("topicId") long topicId, 
			@RequestParam("addQuestion") String questionText, 
			@RequestParam("difficultyLevel") byte difficultyLevel,
			@RequestParam(value="image_url",defaultValue="") String imageUploadUrl,
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
				  String imageUrl = CDNConstants.CLOUDINARY_RELATIVE_URL + StringUtils.substringBeforeLast(imageUploadUrl, CDNConstants.IMAGE_UPLOAD_SEPARATOR);
				  question = questionService.addQuestion(topicId, difficultyLevel, imageUrl, questionText);
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
	
	@RequestMapping(value="/editQuestionImage", method=RequestMethod.POST)	
	public ModelAndView uploadImageForQuestion(@RequestParam("topicId") Long topicId,@RequestParam("questionId") Long questionId,
			@RequestParam("image_url") String imageUploadUrl, Model model, HttpServletRequest request) {
				
		String error = "";
		String success = "";
		
		Question question = questionService.findById(questionId);
		if(question == null){
			error = "The question you are trying to edit the image for doesn't exist in the system";
		}
		else if(StringUtils.isEmpty(imageUploadUrl)){
			error = "The image you are trying to upload has failed to upload on the CDN or you haven't chosen any image to upload";
		}
		
		else{
			try{
				
			    String imageUrl = CDNConstants.CLOUDINARY_RELATIVE_URL + StringUtils.substringBeforeLast(imageUploadUrl, CDNConstants.IMAGE_UPLOAD_SEPARATOR);
			    question.setImageUrl(imageUrl);
			    questionService.saveQuestion(question);
				
				success = "question image successfully edited";
			}
			catch(Exception e){
				error ="internal error while editing image for the question :"+e.getMessage();
			}
		}			
		
		return postRequestProcessing(topicId, error,success,request,model);
		
	}
	
	
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

