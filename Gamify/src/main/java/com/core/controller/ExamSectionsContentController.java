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
import com.core.service.ExamSectionService;
import com.core.service.ExamService;


@Controller
@RequestMapping(value="/content/examSections")
public class ExamSectionsContentController {
	
		
	@Autowired
	private ExamService examService;
	

	@Autowired
	private ExamSectionService examSectionService;
	
	private Validator validator;
	
	@Autowired
	public ExamSectionsContentController(Validator validator) {
		this.validator = validator;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView show( @RequestParam("examId") Long examId, Model model, HttpServletRequest request) {
		String success = "";
		String error = "";
		model.addAttribute("error",error);
		return postRequestProcessing(examId, error, success, request, model);
		
	}
	
	private ModelAndView postRequestProcessing(Long examId, String error, String success, HttpServletRequest request, Model model){
		model.addAttribute("error",error);	
		model.addAttribute("success",success);	
		model.addAttribute("user",(User)request.getSession().getAttribute(GameConstants.SESSION_VARIABLE_LOGGEDIN_USER)); 
		Exam exam = examService.findById(examId);
		model.addAttribute("examSections", exam.getExamSections());	
		model.addAttribute("examId",examId);
		model.addAttribute("examName",exam.getExamName());
		return new ModelAndView("contentAdmin/examSections");	
	}
	
	@RequestMapping(value="/addExamSection", method=RequestMethod.POST)
	public ModelAndView addExamSection(@RequestParam("examId") Long examId, @RequestParam("addexamSection") String examSectionName, Model model, HttpServletRequest request) {
		
		String success="";
		String error="";		
		if(StringUtils.isEmpty(examSectionName)){
			error="exam section name cannot be empty";			
		}
		else{
			List<ExamSection> examSections = examSectionService.findByNameAndExam(examId, examSectionName);
			if(examSections!= null){
				error="You cannot add a duplicate exam section";
			}
			else{
				try{
				   examSectionService.addExamSection(examId, examSectionName);
				   success = "exam section successfully added";
				}
				catch(Exception e){
					error="internal error while adding exam section:"+e.getMessage();
				}
			}			
		}
		
		return postRequestProcessing(examId, error,success,request,model);
	}
	
	
	@RequestMapping(value="/editExamSection", method=RequestMethod.POST)	
	public ModelAndView editExamSection(@RequestParam("examId") Long examId,@RequestParam("examSectionId") Long examSectionId, 
			@RequestParam("editexamSection") String newExamSectionName , Model model, HttpServletRequest request) {
				
		String error = "";
		String success = "";
		if(StringUtils.isEmpty(newExamSectionName)){			
			error = "exam section name cannot be empty";			
		}
		else{
			ExamSection examSection = examSectionService.findById(examSectionId);
			if(examSection == null){
							
				error = "The exam section you are trying to edit doesn't exist in the system";
			}
			else if(examSection.getName().equals(newExamSectionName)){
				
				error = "You cannot add a duplicate exam section";
			}
			else{
				
				List<ExamSection> examSections = examSectionService.findByNameAndExam(examId, newExamSectionName);
				if(examSections != null){
					error = "An exam section with this name already exists in the system. Please chose a different name";
				}
				else{
					try{
					   
					   examSectionService.editExamSection(examSectionId, newExamSectionName);
					   success = "exam section successfully edited";
					}
					catch(Exception e){
						error ="internal error while editing exam section:"+e.getMessage();
					}
				}
				
			}			
		}		
		return postRequestProcessing(examId, error,success,request,model);
		
	}
	
	@RequestMapping(value="/disableExamSection", method=RequestMethod.POST)	
	public ModelAndView disableExamSection(@RequestParam("examId") Long examId, @RequestParam("examSectionId") Long examSectionId,Model model, HttpServletRequest request) {
				
	     return toggleExamSectionState(examId, examSectionId, model, request, EntityStateENUM.INACTIVE.name());
		
	}
	
	@RequestMapping(value="/enableExamSection", method=RequestMethod.POST)	
	public ModelAndView enableExamSection(@RequestParam("examId") Long examId, @RequestParam("examSectionId") Long examSectionId, Model model, HttpServletRequest request) {
				
	     return toggleExamSectionState(examId, examSectionId, model, request, EntityStateENUM.ACTIVE.name());
		
	}
	
	private ModelAndView toggleExamSectionState(Long examId,Long examSectionId, Model model,HttpServletRequest request, String state){
		String error = "";
		String success = "";
	    ExamSection examSection = examSectionService.findById(examSectionId);
		if(examSection == null){
						
			error = "The exam section you are trying to disable doesn't exist in the system";
		}
		else{
			
			examSection.setState(state);
			try{
				   
			   examSectionService.saveExamSection(examSection);
			   success = "exam section state successfully changed";
			}
			catch(Exception e){
				error ="internal error while toggling exam section state:"+e.getMessage();
			}
			
			
		}			
	
		return postRequestProcessing(examId, error,success,request,model);
	}
	
	
	
	
	
	
	
}

