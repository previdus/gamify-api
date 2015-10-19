package com.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

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
import com.core.domain.AnswerKey;
import com.core.domain.Option;
import com.core.domain.Question;
import com.core.domain.User;
import com.core.service.AnswerKeyService;
import com.core.service.OptionService;
import com.core.service.QuestionService;


@Controller
@RequestMapping(value="/content/options")
public class OptionsContentController {
	
		
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private OptionService optionService;
	
	@Autowired
	private AnswerKeyService answerKeyService;
	
	private Validator validator;
	
	@Autowired
	public OptionsContentController(Validator validator) {
		this.validator = validator;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView show( @RequestParam("questionId") Long questionId, Model model, HttpServletRequest request) {
		String success = "";
		String error = "";
		model.addAttribute("error",error);
		return postRequestProcessing(questionId, error, success, request, model);
		
	}
	
	private ModelAndView postRequestProcessing(Long questionId, String error, String success, HttpServletRequest request, Model model){
		model.addAttribute("error",error);	
		model.addAttribute("success",success);	
		model.addAttribute("user",(User)request.getSession().getAttribute(GameConstants.SESSION_VARIABLE_LOGGEDIN_USER)); 
		Question question = questionService.findById(questionId);
		AnswerKey answerKey = answerKeyService.getAnswerKey(question);
		model.addAttribute("answerKey", answerKey);	
		List<Option> options = optionService.getOptions(question);		
		model.addAttribute("options", options);	
		model.addAttribute("questionId",questionId);		
		model.addAttribute("topicName", question.fetchTopic().getName());
		model.addAttribute("examSectionName", question.fetchTopic().fetchExamSection().getName());
		model.addAttribute("examName", question.fetchTopic().fetchExamSection().fetchExam().getExamName());
		model.addAttribute("questionText",question.getQuestionText());
		model.addAttribute("htmlEscapedJson",CDNConstants.getRequiredEscapedHtmlJsonStringForCloudinaryImageUpload());
		return new ModelAndView("contentAdmin/options");	
	}
	
	@RequestMapping(value="/addOption", method=RequestMethod.POST)
	public ModelAndView addOption(@RequestParam("questionId") long questionId, 
			@RequestParam("addOption") String optionText, 
			@RequestParam("order") int order,
			@RequestParam(value="image_url",defaultValue="") String imageUploadUrl,
			Model model, 
			HttpServletRequest request) {
		
		Option option = null;
		String success="";
		String error="";		
		if(StringUtils.isEmpty(optionText)){
			error="option text cannot be empty";			
		}
		else if(order == 0){
			error="option should have an order chosen";
		}
		else{
			List<Option> options = optionService.findByOptionTextAndQuestion(questionId, optionText);
			if(options!= null){
				error="You cannot add a duplicate option";
			}
			else{
				options = optionService.findActiveOptionByOrderAndQuestion(questionId, order);
				if(options!= null){
					error="An option with the same order already exists for this question. Please choose a different order";
				} else{
					try{
						 String imageUrl = CDNConstants.CLOUDINARY_RELATIVE_URL + StringUtils.substringBeforeLast(imageUploadUrl, CDNConstants.IMAGE_UPLOAD_SEPARATOR);
						  option = optionService.addOption(questionId, imageUrl, optionText, order);
						   success = "option successfully added";
						}
						catch(Exception e){
							error="internal error while adding option:"+e.getMessage();
						}
				}
				
			}			
		}
		
		return postRequestProcessing(questionId, error,success,request,model);
	}
	
	
	@RequestMapping(value="/editOption", method=RequestMethod.POST)	
	public ModelAndView editOption(@RequestParam("questionId") Long questionId,@RequestParam("optionId") Long optionId, 
			@RequestParam("editOption") String newOptionText , Model model, HttpServletRequest request) {
				
		String error = "";
		String success = "";
		if(StringUtils.isEmpty(newOptionText)){			
			error = "option text cannot be empty";			
		}
		else{
			Option option = optionService.findById(optionId);
			if(option == null){
							
				error = "The option you are trying to edit doesn't exist in the system";
			}
			else if(option.getText().equals(newOptionText)){
				
				error = "You cannot add a duplicate option";
			}
			else{
				
				List<Option> options = optionService.findByOptionTextAndQuestion(questionId, newOptionText);
				if(options != null){
					error = "A option with this text already exists in the system. Please type  a different one";
				}
				else{
					try{
							   
							   optionService.editOptionText(optionId, newOptionText);
							   success = "option text successfully edited";
							}
							catch(Exception e){
								error ="internal error while editing option:"+e.getMessage();
							}
					}
				
				}
				
			}			
				
		return postRequestProcessing(questionId, error,success,request,model);		
	}
	
	@RequestMapping(value="/editOptionImage", method=RequestMethod.POST)	
	public ModelAndView uploadImageForQuestion(@RequestParam("questionId") Long questionId,@RequestParam("optionId") Long optionId,
			@RequestParam("image_url") String imageUploadUrl, Model model, HttpServletRequest request) {
				
		String error = "";
		String success = "";
		
		Option option = optionService.findById(optionId);
				
		if(option == null){
			error = "The option you are trying to edit the image for doesn't exist in the system";
		}
		else if(StringUtils.isEmpty(imageUploadUrl)){
			error = "The image you are trying to upload has failed to upload on the CDN or you haven't chosen any image to upload";
		}
		
		else{
			try{
				
			    String imageUrl = CDNConstants.CLOUDINARY_RELATIVE_URL + StringUtils.substringBeforeLast(imageUploadUrl, CDNConstants.IMAGE_UPLOAD_SEPARATOR);
			    option.setImageUrl(imageUrl);
			    optionService.saveOption(option);
				
				success = "option image successfully edited";
			}
			catch(Exception e){
				error ="internal error while editing image for the option :"+e.getMessage();
			}
		}			
		
		return postRequestProcessing(questionId, error,success,request,model);
		
	}
	
	
	@RequestMapping(value="/editOptionOrder", method=RequestMethod.POST)	
	public ModelAndView editOptionLevel(@RequestParam("questionId") Long questionId,@RequestParam("optionId") Long optionId,@RequestParam("order") int order, Model model, HttpServletRequest request) {
				
		String error = "";
		String success = "";
		
		Option option = optionService.findById(optionId);
		if(option == null){
			error = "The option you are trying to change the order on doesn't exist in the system";
		}
		
		else{
			List<Option> options = optionService.findActiveOptionByOrderAndQuestion(questionId, order);
			if(options!= null){
				error="An option with the same order already exists for this question. Please choose a different order";
			} else{
				try{
					
					optionService.editOptionOrdr(optionId, order);
					success = "option order successfully changed";
				}
				catch(Exception e){
					error ="internal error while editing option order :"+e.getMessage();
				}

			}
					
		}			
		
		return postRequestProcessing(questionId, error,success,request,model);
		
	}
	
	
	
	@RequestMapping(value="/chooseAnswerKey", method=RequestMethod.GET)	
	public ModelAndView chooseAnswerKey(@RequestParam("questionId") Long questionId, @RequestParam("optionId") Long optionId,Model model, HttpServletRequest request) {
				
		answerKeyService.saveAnswerKey(questionId, optionId);
		String error = "";
		String success = "";
		return postRequestProcessing(questionId, error,success,request,model);
		
	}
	
	@RequestMapping(value="/disableOption", method=RequestMethod.GET)	
	public ModelAndView disableOption(@RequestParam("questionId") Long questionId, @RequestParam("optionId") Long optionId,Model model, HttpServletRequest request) {
				
	     return toggleOptionState(questionId, optionId, model, request, EntityStateENUM.INACTIVE.name());
		
	}
	
	@RequestMapping(value="/enableOption", method=RequestMethod.GET)	
	public ModelAndView enableOption(@RequestParam("questionId") Long questionId, @RequestParam("optionId") Long optionId, Model model, HttpServletRequest request) {
				
	     return toggleOptionState(questionId, optionId, model, request, EntityStateENUM.ACTIVE.name());
		
	}
	
	private ModelAndView toggleOptionState(Long questionId,Long optionId, Model model,HttpServletRequest request, String state){
		String error = "";
		String success = "";
	    Option option = optionService.findById(optionId);
		if(option == null){
						
			error = "The option you are trying to disable doesn't exist in the system";
		}
		else{
			option.setState(EntityStateENUM.valueOf(state));
			try{
				   
			   optionService.saveOption(option);
			   success = "option state successfully changed";
			}
			catch(Exception e){
				error ="internal error while toggling  option state:"+e.getMessage();
			}
			
			
		}			
	
		return postRequestProcessing(questionId, error,success,request,model);
	}
	
	
	
	
	
	
	
}

