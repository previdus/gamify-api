package com.core.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.core.service.AccountVerificationService;

@Controller
@RequestMapping(value = "/api/account")
public class ApiAccountVerificationController {

	@Autowired
	private AccountVerificationService accountVerificationService;

	@RequestMapping(value = "/verify-user-email/{verificationCode}", method = RequestMethod.GET,produces="application/json")
	public boolean verifyEmail( 
			@PathVariable("verificationCode") String verificationCode, 
			Model model, HttpServletRequest request) {
		return accountVerificationService.verifyUserEmail(verificationCode);
	}
	
	@RequestMapping(value = "/verify-parent-email/{userCode}/{verificationCode}", method = RequestMethod.GET,produces="application/json")
	public boolean verifyParentEmail(
			@PathVariable("userCode") String userCode,
			@PathVariable("verificationCode") String verificationCode, 
			Model model, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("account/email-verification");
		return  accountVerificationService.verifyParentEmail(userCode, verificationCode);
	}


}
