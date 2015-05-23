package com.core.api.controller;

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
			@RequestParam("email") String email,
			@RequestParam("displayName") String displayName,
			@RequestParam("gender") String gender,
			@RequestParam("facebookId") String facebookId) {

		// validation
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
					"iinvalid password format. Your password must contain 6 to 20 characters");
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

		User user = new User(userName, password, email, displayName,
				resolveGender(gender), facebookId,
				GenericUtil.generateFacebookProfileSmallImageUrl(facebookId));
		user = userService.saveUser(user);
		// null check
		if (user == null) {
			return createApiResult(-1,
					"there was a problem registering the user");
		}
		// successful registration so autologin
		ApiResult apr = apiLoginController.loginPost(userName, password);
		apr.setMessage("Registration successful. " + apr.getMessage());
		return apr;

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
