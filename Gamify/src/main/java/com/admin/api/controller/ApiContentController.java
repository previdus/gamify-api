package com.admin.api.controller;

import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.core.api.beans.ApiResult;
import com.core.api.beans.ExamsResult;
import com.core.api.enumerator.ApiResultStatus;

import com.core.domain.lms.Exam;
import com.core.service.ExamService;


@Controller
@RequestMapping(value="/api/content/exams")
public class ApiContentController {
	
	@Autowired
	private ExamService examService;
	
//	@Autowired
//	private ReloadableResourceBundleMessageSource messageSource;

	@RequestMapping(value="/addexam",method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public ApiResult addExam(@RequestParam("examName")String examName) {
		// TODO Auto-generated method stub
		ApiResult apiResult =  new ApiResult();
		Exam exam = examService.addExam(examName);
		if(exam != null){
			apiResult.setStatus(ApiResultStatus.SUCCESS.code());
			//apiResult.setMessage(messageSource.getMessage("dataentry.addexam.success",null,Locale.US));
			apiResult.setMessage("successfully added exam");
		}
		else{
			apiResult.setStatus(ApiResultStatus.FAILURE.code());
			//apiResult.setMessage(messageSource.getMessage("dataentry.addexam.success",null,Locale.US));
			apiResult.setMessage("failed to add exam");
		}
		return apiResult;
		
	}
	
	@RequestMapping(value="/editExam", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ApiResult editExam(@RequestParam("examId") Long examId, @RequestParam("editexam") String newExamName) {
				
		ApiResult apiResult = new ApiResult();
		if(StringUtils.isEmpty(newExamName)){			
			apiResult.setMessage("exam name cannot be empty");			
		}
		else{
			Exam exam = examService.findById(examId);
			if(exam == null){
				apiResult.setStatus(ApiResultStatus.FAILURE.code());				
				apiResult.setMessage("The exam you are trying to edit doesn't exist in the system");
			}
			else if(exam.getExamName().equals(newExamName)){
				apiResult.setStatus(ApiResultStatus.FAILURE.code());
				apiResult.setMessage("You cannot add a duplicate exam");
			}
			else{
				try{
				   
				   examService.editExam(examId, newExamName);
				   apiResult.setMessage("exam successfully edited");
				}
				catch(Exception e){
					apiResult.setMessage("internal error while adding exam:"+e.getMessage());
				}
			}			
		}		
		return apiResult;
	}
	
	@RequestMapping(value="/showexams",method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public ExamsResult showExams() {
		// TODO Auto-generated method stub
		ExamsResult examsResult =  new ExamsResult();
		List<Exam> exams = examService.showExams();
		if(!CollectionUtils.isEmpty(exams)){
			examsResult.setStatus(ApiResultStatus.SUCCESS.code());
			examsResult.setExams(exams);		
		}
		return examsResult;
		
	}
}
