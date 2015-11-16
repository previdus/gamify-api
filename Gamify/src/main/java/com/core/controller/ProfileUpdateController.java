package com.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.core.constants.GameConstants;
import com.core.domain.User;
import com.core.service.AccountVerificationService;

@Controller
@RequestMapping(value = "/profile")
public class ProfileUpdateController {

	@Autowired
	private AccountVerificationService accountVerificationService;

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView verifyEmail(Model model, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("account/profile-update");
		User user = (User) request.getSession().getAttribute(GameConstants.SESSION_VARIABLE_LOGGEDIN_USER);
		modelAndView.addObject("user", user);
		return modelAndView;
	}
}
