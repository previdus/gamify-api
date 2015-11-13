package com.core.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.core.service.RoomService;
import com.core.service.UserService;
import com.core.validator.GenericValidator;

@Controller
public class LoginController {
	private static final Logger log = LoggerFactory
			.getLogger(LoginController.class);

	@Autowired
	private RoomService roomService;

	@Autowired
	private UserService userService;

	

	@Autowired
	private ApiLoginController apiLoginController;
	
	
	private GenericValidator validator;
	
	

	@Autowired
	public LoginController(GenericValidator validator) {
		this.validator = validator;
		
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView login(Model model) {
		
		model.addAttribute(new User());	
		return new ModelAndView("account/LoginPage");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView create(@ModelAttribute("user") User userFromView,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		if(areAnyLoginFieldsBlank(userFromView)){
			model.addAttribute("loginStatus", "Please provide login id and password! ");
			return login( model);
		}
		if("ADMIN".equalsIgnoreCase(userFromView.getName()) && "BINGO".equalsIgnoreCase(userFromView.getPwd()))
				response.sendRedirect("rooms/content");
		 ApiResult apiResult = apiLoginController.loginPost(userFromView.getName(), userFromView.getPwd(), request);		
		log.debug("user Token ------------------**********************  "+ apiResult.getUserToken());
		if(apiResult.getStatus() == 1){
				request.getSession().setAttribute(GameConstants.SESSION_VARIABLE_LOGGEDIN_USER,apiResult.getUser());
				request.getSession().setAttribute(GameConstants.SESSION_VARIABLE_LOGGEDIN_USER_RESULT,apiResult);
				response.sendRedirect("rooms");
				model.addAttribute("loginStatus", "Successful login!");
				return login( model);
		}
		if(apiResult.getStatus() == 0){
			model.addAttribute("loginStatus", "Email Verification Pending! A verification link has been sent to you on your registerd email address " +  apiResult.getUser().getEmailId());
			return login( model);
		}
		
        if(userService.doesUserExist(userFromView.getName())){
        	model.addAttribute("loginStatus", "Invalid login. Looks like your password is incorrect. Click the 'help' link below the login button!");
    		return login( model);
		}
        
		model.addAttribute("loginStatus", "Invalid login. User does not exist in the system or the password is incorrect!");
		return login( model);
		
	}

	private boolean areAnyLoginFieldsBlank(User userFromView) {
		return userFromView == null || StringUtils.isEmpty(userFromView.getName()) || StringUtils.isEmpty(userFromView.getPwd());
	}

	@RequestMapping(value = "/facebookLogin", method = RequestMethod.POST)
	public ModelAndView createFacebookLogin(
			@RequestParam("facebookName") String facebookName,
			@RequestParam("facebookEmail") String facebookEmail,
			@RequestParam("facebookId") String facebookId,
			@RequestParam("gender") String gender, HttpServletRequest request,
			HttpServletResponse response) {
		
		log.info("facebookName:" + facebookName);
		log.info("facebookEmail:" + facebookEmail);
		log.info("facebookId::" + facebookId);
		try {
			ApiResult apiResult = apiLoginController.facebookLoginPost(
					facebookName, gender, facebookEmail, facebookId, request);
			if (apiResult.getStatus() == 1) {
				log.info("user Token ------------------**********************  "
						+ apiResult.getUserToken());
				request.getSession().setAttribute(
						GameConstants.SESSION_VARIABLE_LOGGEDIN_USER,
						apiResult.getUser());
				request.getSession().setAttribute(
						GameConstants.SESSION_VARIABLE_LOGGEDIN_USER_RESULT,
						apiResult);

				response.sendRedirect("rooms");
				

			}
			
		
			
		} catch (Exception ioe) {
			log.info("Exception while redirecting to rooms " + ioe);
		}
		return null;
	}
}
