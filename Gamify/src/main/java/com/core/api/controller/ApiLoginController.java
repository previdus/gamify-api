package com.core.api.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.ApiResult;
import com.core.constants.UserAccountStatus;
import com.core.domain.User;
import com.core.manager.UserManager;
import com.core.service.EmailNotificationService;
import com.core.service.RoomService;
import com.core.service.UserService;
import com.core.util.GenericUtil;

import emailTemplates.EmailNotificationSender;

@Controller
@RequestMapping(value = "/api/login")
public class ApiLoginController {
	
	private static final Logger log = LoggerFactory.getLogger(ApiLoginController.class);

	@Autowired
	private RoomService roomService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailNotificationService emailNotificationService;

	@Autowired
	private ApiRegistrationController apiRegistrationController;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult loginPost(@RequestParam("userName") String userName,
			@RequestParam("password") String password, HttpServletRequest request) throws Exception {
		ApiResult apr = new ApiResult();
		User userFromRepository = userService.getUser(userName, password);
		if(userFromRepository == null)
		{
			apr.setMessage("user not found");
			apr.setRedirectLink("");
			apr.setStatus(-1);
			return apr;
		}
		if(!UserAccountStatus.ACTIVE.equals(userFromRepository.getUserAccountStatus())){
			//emailNotificationService.sendAccountActivationEmail(request.getServerName(), userFromRepository.getEmailId());
			apr.setMessage("Email Verification Needed");
			apr.setRedirectLink("");
			apr.setStatus(0);
			return apr;
			}
			
			String userToken = (UserManager.generateUserToken()).toString();
			UserManager.userTokenMap.put(userToken, userFromRepository);
			apr.setMessage("successfully logged in");
			apr.setRedirectLink("/api/rooms/" + userToken);
			// TODO: Set a particular status
			apr.setStatus(1);
			apr.setUser(userFromRepository);
			apr.setUserToken(userToken);
		
		return apr;

	}

	@RequestMapping(value = "/facebook", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult facebookLoginPost(
			@RequestParam("facebookName") String facebookName,
			@RequestParam("gender") String gender,
			@RequestParam("facebookEmail") String facebookEmail,
			@RequestParam("facebookId") String facebookId, HttpServletRequest request) throws Exception {

		User userFromRepository = userService.getUserByFacebookId(facebookId);
		if (userFromRepository == null) {
			log.info("before querying user by facebookEmail");
			userFromRepository = userService.getUserByEmail(facebookEmail);
			log.info("after querying user by facebookEmail");
			if (userFromRepository != null) {
				userFromRepository.setFacebookId(facebookId);
				userService.saveUser(userFromRepository);
			}
		}
		if (userFromRepository != null) {
			return loginPost(userFromRepository.getName(),
					userFromRepository.getPwd(), request);
		} else {
			log.info("before generating temp password");
			String tempPassword = GenericUtil
					.generateTemporaryPasswordBasedOnUserName(facebookName);
			log.info("before registering user");
			ApiResult apr = apiRegistrationController.registerPost(
					facebookEmail, tempPassword, facebookEmail,"", facebookName,
					gender, facebookId, request );
			if (apr.getStatus() == 1) {
				log.info("successfully created user with his facebook credentials");
				List<String> recepients = new LinkedList<String>();
				recepients.add(facebookEmail);
				String emailBody = "You have been assigned a temporary user id and password to login. You can change these later from the edit profile page of our application Your user Id is "
						+ facebookEmail + ". Your password is " + tempPassword;
				EmailNotificationSender.sendResetPasswordMail(null, recepients,emailBody);
				apr.setMessage("You have been registered to our application. Your temporary user id is "
						+ facebookEmail
						+ " and password is "
						+ tempPassword
						+ " An email has already been sent to "
						+ " the address "
						+ facebookEmail
						+ " with the details. " + apr.getMessage());
			} else {
				log.info("failed to  create user with his facebook credentials");
				apr.setMessage("Unable to use your facebook credentials to auto register to our application. "
						+ apr.getMessage());
			}
			return apr;

		}

	}

}
