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
import com.core.service.GenerateDummyDataInDatabase;
import com.core.service.UserService;
import com.core.util.GenericUtil;
import com.core.validator.GenericValidator;
import emailTemplates.EmailNotificationSender;

@Controller
@RequestMapping(value = "/api/resetPass")
public class ApiForgotPasswordController {

	@Autowired
	private UserService userService;

	@Autowired
	private GenerateDummyDataInDatabase generateDummyDataInDatabase;

	private GenericValidator validator;

	@Autowired
	public ApiForgotPasswordController(GenericValidator validator) {
		this.validator = validator;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult sendEmailToResetPassword(
			@RequestParam("email") String email) {
		ApiResult apr = new ApiResult();
		if (!validator.emailValidate(email)) {
			apr.setStatus(-1);
			apr.setMessage("invalid email format");
			return apr;
		}

		User user = userService.getUserByEmail(email);
		if (user == null) {
			apr.setStatus(-1);
			apr.setMessage("It seems you haven't registered in our system. Please register!");
			return apr;
		}
		String tempPassword = GenericUtil
				.generateTemporaryPasswordBasedOnUserName(user.getName());
		user.setPwd(tempPassword);
		userService.saveUser(user);
		List<String> recepients = new LinkedList<String>();
		recepients.add(email);
		String emailBody = "Your password has been reset. It has been set to "
				+ tempPassword;
		EmailNotificationSender.sendResetPasswordMail(null, recepients,
				emailBody);
		apr.setStatus(1);
		apr.setMessage("Your password has been successfully reset and been sent to the email provided. You can change your password later on the eidt profile page");
		return apr;

	}

}
