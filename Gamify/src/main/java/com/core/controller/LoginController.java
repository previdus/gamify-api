package com.core.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.core.api.beans.ApiResult;
import com.core.api.controller.ApiLoginController;
import com.core.constants.GameConstants;
import com.core.domain.User;
import com.core.manager.ThreadManager;
import com.core.service.GenerateDummyDataInDatabase;
import com.core.service.RoomService;
import com.core.service.UserService;
import com.core.validator.GenericValidator;


@Controller

public class LoginController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GenerateDummyDataInDatabase generateDummyDataInDatabase;
	
	@Autowired
	private ApiLoginController apiLoginController;
	
	private GenericValidator validator;
	
	@Autowired
	public LoginController(GenericValidator validator) {
		this.validator = validator;
		ThreadManager.startDaemonQueueManager();
	}
	
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView login(Model model) {
		model.addAttribute(new User());
		List<String> recepients = new LinkedList<String>();
		recepients.add("ricky.rungta@gmail.com");
		recepients.add("gopal.yami@gmail.com");
		//EmailNotificationSender.sendResetPasswordMail(null, recepients, "Test Mail For LMS System");
		
		return new ModelAndView("account/LoginPage");//, "command", new User());
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView create(@ModelAttribute("user") User userFromView,  BindingResult result,HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView modelAndView = new ModelAndView("account/LoginPage");
	    if(userFromView.getName().equals("mock")){
	    	generateDummyDataInDatabase.generateData();
	    }
	    User userFromRepository = userService.getUser(userFromView.getName(), userFromView.getPwd());
		if(userFromRepository != null){			
			try{
				 ApiResult apiResult = apiLoginController.loginPost(userFromView.getName(), userFromView.getPwd());
				 log.info("user Token ------------------**********************  " + apiResult.getUserToken());
				request.getSession().setAttribute(GameConstants.SESSION_VARIABLE_LOGGEDIN_USER, userFromRepository);	
				request.getSession().setAttribute(GameConstants.SESSION_VARIABLE_LOGGEDIN_USER_RESULT, apiResult);
				response.sendRedirect("rooms");
				//return new ModelAndView("account/rooms");
			}catch(IOException ioe){
				System.out.println("Exception while redirecting to rooms " + ioe);
			}
			return null;
		}		       
		return  modelAndView;		    
	}	
	
	@RequestMapping(value="/facebookLogin",method=RequestMethod.POST)
	public ModelAndView createFacebookLogin(
			@RequestParam("facebookName") String facebookName,
			@RequestParam("facebookEmail") String facebookEmail, 
			@RequestParam("facebookId") String facebookId,
			@RequestParam("gender") String gender,
            HttpServletRequest request,
            HttpServletResponse response) {
	    ModelAndView modelAndView = new ModelAndView("account/LoginPage");
	    log.info("facebookName:"+facebookName);
	    log.info("facebookEmail:"+facebookEmail);
	    log.info("facebookId::"+facebookId);
		try{
			 ApiResult apiResult = apiLoginController.facebookLoginPost(facebookName,gender, facebookEmail,facebookId);
			 if(apiResult.getStatus() == 1){
				 log.info("user Token ------------------**********************  " + apiResult.getUserToken());
				 request.getSession().setAttribute(GameConstants.SESSION_VARIABLE_LOGGEDIN_USER, apiResult.getUser());	
				 request.getSession().setAttribute(GameConstants.SESSION_VARIABLE_LOGGEDIN_USER_RESULT, apiResult);
				
				 response.sendRedirect("rooms");
				 return null;
				
				
			 }
			//return new ModelAndView("account/rooms");
		}catch(Exception ioe){
			System.out.println("Exception while redirecting to rooms " + ioe);
		}			       
		return  modelAndView;		    
	}	
}
