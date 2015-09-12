package com.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.core.domain.User;
import com.core.service.AccountVerificationService;

@Controller
@RequestMapping(value = "/account")
public class AccountVerificationController {

	@Autowired
	private AccountVerificationService accountVerificationService;

	@RequestMapping(value = "/verify-user-email/{verificationCode}", method = RequestMethod.GET)
	public ModelAndView verifyEmail(
			@PathVariable("verificationCode") String verificationCode, 
			Model model, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("account/email-verification");
		boolean isVerified =  accountVerificationService.verifyUserEmail(verificationCode);
		String verificationMessage;
		if(isVerified)
				verificationMessage = "Your Account is verified! You may start playing!";
			else
				verificationMessage = "Opps! This seems an invalid verification link! To regenrate the link, kindly try and login and you shall receive a valid link at your registered email address!";
		modelAndView.addObject("verificationMessage", verificationMessage);
		modelAndView.addObject("user", new User());
		return modelAndView;
	}
	
	@RequestMapping(value = "/verify-parent-email/{userCode}/{verificationCode}", method = RequestMethod.GET)
	public ModelAndView verifyParentEmail(
			@PathVariable("userCode") String userCode,
			@PathVariable("verificationCode") String verificationCode, 
			Model model, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("account/email-verification");
		boolean isVerified =  accountVerificationService.verifyParentEmail(userCode, verificationCode);
		String verificationMessage;
		if(isVerified)
				verificationMessage = "Your Account is verified! You may start playing!";
			else
				verificationMessage = "Opps! This seems an invalid verification link! To regenrate the link, kindly try and login and you shall receive a valid link at your registered email address!";
		modelAndView.addObject("verificationMessage", verificationMessage);
		modelAndView.addObject("user", new User());
		return modelAndView;
	}
}
