package com.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.core.domain.lms.Topic;
import com.core.service.TopicService;


@Controller
@RequestMapping(value="/content/question/add")
public class QuestionContentController {
	
	@Autowired
	private TopicService topicService;
	
	@RequestMapping(method=RequestMethod.GET, value="/{topicId}")
	public ModelAndView show(@PathVariable Long topicId, Model model, HttpServletRequest request) {
		Topic topic = topicService.findById(topicId);
		model.addAttribute("topicId",topicId);
		model.addAttribute("topicName",topic.getName());
		model.addAttribute("examSectionName", topic.fetchExamSection().getName());
		model.addAttribute("examName", topic.fetchExamSection().fetchExam().getExamName());
		return new ModelAndView("contentAdmin/questionEntry");
	}
	
	
	
}

