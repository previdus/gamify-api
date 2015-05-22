package com.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.core.api.beans.ApiResult;
import com.core.api.enumerator.ApiResultStatus;
import com.core.constants.EntityStateENUM;
import com.core.constants.GameConstants;
import com.core.domain.User;
import com.core.domain.lms.Exam;
import com.core.service.ExamService;
import com.core.service.RoomService;


@Controller
@RequestMapping(value="/content")
public class ContentController {
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private ExamService examService;
	
	private Validator validator;
	
	@Autowired
	public ContentController(Validator validator) {
		this.validator = validator;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView show( Model model, HttpServletRequest request) {
		roomService.getRoomWithAllExams().setRoomName("Main Room");
		String success = "";
		String error = "";
		return postRequestProcessing(error,success,request,model);	}
	
	@RequestMapping(value="/addExam", method=RequestMethod.POST)
	public ModelAndView addExam(@RequestParam("addexam") String examName, Model model, HttpServletRequest request) {
		
		String success="";
		String error="";		
		if(StringUtils.isEmpty(examName)){
			error="exam name cannot be empty";			
		}
		else{
			Exam exam = examService.findByName(examName);
			if(exam!= null){
				error="You cannot add a duplicate exam";
			}
			else{
				try{
				   exam = examService.addExam(examName);
				   success = "exam successfully added";
				}
				catch(Exception e){
					error="internal error while adding exam:"+e.getMessage();
				}
			}			
		}
		
		return postRequestProcessing(error,success,request,model);
	}
	
	
	@RequestMapping(value="/editExam", method=RequestMethod.POST)	
	public ModelAndView editExam(@RequestParam("examId") Long examId, @RequestParam("editexam") String newExamName , Model model, HttpServletRequest request) {
				
		String error = "";
		String success = "";
		if(StringUtils.isEmpty(newExamName)){			
			error = "exam name cannot be empty";			
		}
		else{
			Exam exam = examService.findById(examId);
			if(exam == null){
							
				error = "The exam you are trying to edit doesn't exist in the system";
			}
			else if(exam.getExamName().equals(newExamName)){
				
				error = "You cannot add a duplicate exam";
			}
			else{
				
				exam = examService.findByName(newExamName);
				if(exam != null){
					error = "An exam with this name already exists in the system. Please chose a different name";
				}
				else{
					try{
					   
					   examService.editExam(examId, newExamName);
					   success = "exam successfully edited";
					}
					catch(Exception e){
						error ="internal error while adding exam:"+e.getMessage();
					}
				}
				
			}			
		}		
		return postRequestProcessing(error,success,request,model);
		
	}
	
	@RequestMapping(value="/disableExam", method=RequestMethod.POST)	
	public ModelAndView disableExam(@RequestParam("examId") Long examId, Model model, HttpServletRequest request) {
				
	     return toggleExamState(examId, model, request, EntityStateENUM.INACTIVE.name());
		
	}
	
	@RequestMapping(value="/enableExam", method=RequestMethod.POST)	
	public ModelAndView enableExam(@RequestParam("examId") Long examId, Model model, HttpServletRequest request) {
				
	     return toggleExamState(examId, model, request, EntityStateENUM.ACTIVE.name());
		
	}
	
	private ModelAndView toggleExamState(Long examId,Model model,HttpServletRequest request, String state){
		String error = "";
		String success = "";
	    Exam exam = examService.findById(examId);
		if(exam == null){
						
			error = "The exam you are trying to disable doesn't exist in the system";
		}
		else{
			
			exam.setState(state);
			try{
				   
			   examService.saveExam(exam);
			   success = "exam state successfully changed";
			}
			catch(Exception e){
				error ="internal error while disabling exam:"+e.getMessage();
			}
			
			
		}			
	
		return postRequestProcessing(error,success,request,model);
	}
	
	private ModelAndView postRequestProcessing(String error, String success, HttpServletRequest request, Model model){
		
		model.addAttribute("error",error);
		model.addAttribute("success",success);	
		model.addAttribute("user",(User)request.getSession().getAttribute(GameConstants.SESSION_VARIABLE_LOGGEDIN_USER));    	
		model.addAttribute("room", roomService.getRoomWithAllExams());	
		return new ModelAndView("account/contentEntry");
		
	}
	
	
	
	
	
	
}

