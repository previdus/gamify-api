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

import com.core.constants.EntityStateENUM;
import com.core.constants.GameConstants;
import com.core.domain.User;
import com.core.domain.lms.Exam;
import com.core.domain.lms.ExamSection;
import com.core.domain.lms.Topic;
import com.core.service.ExamSectionService;
import com.core.service.ExamService;
import com.core.service.TopicService;


@Controller
@RequestMapping(value="/content/topics")
public class TopicsContentController {
	
		
	@Autowired
	private ExamSectionService examSectionService;
	

	@Autowired
	private TopicService topicService;
	
	private Validator validator;
	
	@Autowired
	public TopicsContentController(Validator validator) {
		this.validator = validator;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView show( @RequestParam("examSectionId") Long examSectionId, Model model, HttpServletRequest request) {
		String success = "";
		String error = "";
		model.addAttribute("error",error);
		return postRequestProcessing(examSectionId, error, success, request, model);
		
	}
	
	private ModelAndView postRequestProcessing(Long examSectionId, String error, String success, HttpServletRequest request, Model model){
		model.addAttribute("error",error);	
		model.addAttribute("success",success);	
		model.addAttribute("user",(User)request.getSession().getAttribute(GameConstants.SESSION_VARIABLE_LOGGEDIN_USER)); 
		ExamSection examSection = examSectionService.findById(examSectionId);
		
		model.addAttribute("topics", examSection.getTopics());	
		model.addAttribute("examSectionId",examSectionId);
		model.addAttribute("examName", examSection.getExam().getExamName());
		model.addAttribute("examSectionName",examSection.getName());
		return new ModelAndView("contentAdmin/topics");	
	}
	
	@RequestMapping(value="/addTopic", method=RequestMethod.POST)
	public ModelAndView addTopic(@RequestParam("examSectionId") Long examSectionId, @RequestParam("addTopic") String topicName, Model model, HttpServletRequest request) {
		
		String success="";
		String error="";		
		if(StringUtils.isEmpty(topicName)){
			error="topic name cannot be empty";			
		}
		else{
			List<Topic> topics = topicService.findByNameAndExamSection(examSectionId, topicName);
			if(topics!= null){
				error="You cannot add a duplicate topic";
			}
			else{
				try{
				   topicService.addTopic(examSectionId, topicName);
				   success = "topic successfully added";
				}
				catch(Exception e){
					error="internal error while adding topic:"+e.getMessage();
				}
			}			
		}
		
		return postRequestProcessing(examSectionId, error,success,request,model);
	}
	
	
	@RequestMapping(value="/editTopic", method=RequestMethod.POST)	
	public ModelAndView editTopic(@RequestParam("examSectionId") Long examSectionId,@RequestParam("topicId") Long topicId, 
			@RequestParam("editTopic") String newTopicName , Model model, HttpServletRequest request) {
				
		String error = "";
		String success = "";
		if(StringUtils.isEmpty(newTopicName)){			
			error = "topic name cannot be empty";			
		}
		else{
			Topic topic = topicService.findById(topicId);
			if(topic == null){
							
				error = "The topic you are trying to edit doesn't exist in the system";
			}
			else if(topic.getName().equals(newTopicName)){
				
				error = "You cannot add a duplicate topic";
			}
			else{
				
				List<Topic> topics = topicService.findByNameAndExamSection(examSectionId, newTopicName);
				if(topics != null){
					error = "A topic with this name already exists in the system. Please chose a different name";
				}
				else{
					try{
					   
					   topicService.editTopic(topicId, newTopicName);
					   success = "topic successfully edited";
					}
					catch(Exception e){
						error ="internal error while editing topic:"+e.getMessage();
					}
				}
				
			}			
		}		
		return postRequestProcessing(examSectionId, error,success,request,model);
		
	}
	
	@RequestMapping(value="/disableTopic", method=RequestMethod.POST)	
	public ModelAndView disableTopic(@RequestParam("examSectionId") Long examSectionId, @RequestParam("topicId") Long topicId,Model model, HttpServletRequest request) {
				
	     return toggleTopicState(examSectionId, topicId, model, request, EntityStateENUM.INACTIVE.name());
		
	}
	
	@RequestMapping(value="/enableTopic", method=RequestMethod.POST)	
	public ModelAndView enableTopic(@RequestParam("examSectionId") Long examSectionId, @RequestParam("topicId") Long topicId, Model model, HttpServletRequest request) {
				
	     return toggleTopicState(examSectionId, topicId, model, request, EntityStateENUM.ACTIVE.name());
		
	}
	
	private ModelAndView toggleTopicState(Long examSectionId,Long topicId, Model model,HttpServletRequest request, String state){
		String error = "";
		String success = "";
	    Topic topic = topicService.findById(topicId);
		if(topic == null){
						
			error = "The topic you are trying to disable doesn't exist in the system";
		}
		else{
			
			topic.setState(state);
			try{
				   
			   topicService.saveTopic(topic);
			   success = "topic state successfully changed";
			}
			catch(Exception e){
				error ="internal error while toggling  exam state:"+e.getMessage();
			}
			
			
		}			
	
		return postRequestProcessing(examSectionId, error,success,request,model);
	}
	
	
	
	
	
	
	
}

