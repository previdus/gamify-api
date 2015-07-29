package com.core.api.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.ApiResult;
import com.core.domain.User;
import com.core.service.UserService;
import com.core.util.GenericUtil;
import com.core.validator.GenericValidator;

import emailTemplates.EmailNotificationSender;

@Controller
@RequestMapping(value = "/api/register")
public class ApiRegistrationController {

	@Autowired
	private UserService userService;

	private GenericValidator validator;

	@Autowired
	private ApiLoginController apiLoginController;

	@Autowired
	public ApiRegistrationController(GenericValidator validator) {
		this.validator = validator;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult registerPost(@RequestParam("userName") String userName,
			@RequestParam("password") String password,
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="parentsEmail", required=false) String parentsEmail, 
			@RequestParam("displayName") String displayName,
			@RequestParam("gender") String gender,
			@RequestParam(value="facebookId", required = false) String facebookId) {

		// validation
		
		// email format validation
				if (!validator.emailValidate(parentsEmail)) {

					return createApiResult(-1, "invalid parents email format");
				}
		// email format validation
		if (!validator.emailValidate(email)) {

			return createApiResult(-1, "invalid email format");
		}
		// username format validation
		if (!validator.userNameValidate(userName)) {

			return createApiResult(-1, "invalid userName format");
		}

		// password format validation
		if (!validator.passwordValidate(password)) {

			return createApiResult(-1,
					"invalid password format. Your password must contain 6 to 20 characters");
		}

		// email already exists validation
		if (userService.getUserByEmail(email) != null) {

			return createApiResult(-1,
					"the email address seems to be already registered in the system");
		}
		// username already exists validation
		if (userService.getUserByName(userName) != null) {

			return createApiResult(-1,
					"the userName seems to be already registered in the system");
		}
		// end validation
		String imageUrl = (facebookId != null && facebookId.trim().length() > 0)? GenericUtil.generateFacebookProfileSmallImageUrl(facebookId):"";

		User user = new User(userName, password, email, displayName,
				resolveGender(gender), facebookId,imageUrl);
		user = userService.saveUser(user);
		// null check
		if (user == null) {
			return createApiResult(-1,
					"there was a problem registering the user");
		}
		// successful registration so autologin
		ApiResult apr = apiLoginController.loginPost(userName, password);
		sendWelcomeEmail(userName, email);
		apr.setMessage("Registration successful. An email verification link has been sent to your email id " + email + " Kindly verify it to access the account. " + apr.getMessage());
		return apr;
	}
	
	private void sendWelcomeEmail(String name, String userEmail){
		List<String> recepients = new LinkedList<String>();
		recepients.add(userEmail);
		String emailBody =name +  " Welcome to Lastmanstanding!";
				
		EmailNotificationSender.sendResetPasswordMail(null, recepients,
				emailBody);
		
	}

	private ApiResult createApiResult(int status, String message) {
		ApiResult apr = new ApiResult();
		apr.setStatus(-1);
		apr.setMessage(message);
		return apr;
	}

	// TODO: get rid of checks like this. This should be resolved at database
	// level
	private String resolveGender(String gender) {
		return "male".equals(gender) ? "1" : "0";
	}

}
