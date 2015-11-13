package com.core.api.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.ApiResult;
import com.core.constants.GameConstants;
import com.core.constants.UserAccountStatus;
import com.core.domain.User;
import com.core.service.UserService;
import com.core.util.GenericUtil;
import com.core.validator.GenericValidator;

import emailTemplates.EmailNotificationSender;

@Controller
@RequestMapping(value = "/api/register")
public class ApiRegistrationController {
	
	private static final Logger log = LoggerFactory
			.getLogger(ApiRegistrationController.class);


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
			@RequestParam("cpassword") String cpassword,
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="parentsEmail", required=false) String parentsEmail, 
			@RequestParam("displayName") String displayName,
			@RequestParam("gender") String gender,
			@RequestParam(value="facebookId", required = false) String facebookId, HttpServletRequest request) throws Exception {

		logAllInputFields(userName, email, parentsEmail, displayName, gender,
				facebookId);
		//only validate these if not coming from facebook
		ApiResult apiResult = null;
		if(StringUtils.isEmpty(facebookId))
		{
			apiResult = validateInputFields(userName, password, cpassword, email,
					parentsEmail, displayName, gender);
		    if (apiResult != null) return apiResult;						
				
	    }
		
		User user = null;
		
		apiResult = validateFieldsInTheSystem(userName, email, parentsEmail);
		if(apiResult != null) return apiResult;
		
		
		log.info("after validating all fields before registering");
		// end validation
		String imageUrl = (facebookId != null && facebookId.trim().length() > 0)? GenericUtil.generateFacebookProfileSmallImageUrl(facebookId):"";

		log.info("imageUrl is:"+imageUrl);
		UserAccountStatus accountStatus;
		if(facebookId != null)
			accountStatus = UserAccountStatus.ACTIVE;
		else if((Boolean)GameConstants.CONFIGURATION_MAP.get(GameConstants.IS_EMAIL_VERIFICATION_MANDATORY_KEY))
			accountStatus = UserAccountStatus.EMAIL_VERIFICATION_PENDING;
			else
				accountStatus = UserAccountStatus.ACTIVE;
			try{
				user = new User(userName, password, email, displayName,
						resolveGender(gender), facebookId,imageUrl,parentsEmail, accountStatus);
				log.info("before saving/registering user");
			    user = userService.saveUser(user);
			    log.info("after saving/registering user");
			    try{
			    sendWelcomeEmail(userName, email);
			    }catch(Exception e){
			    	return createApiResult(-8,"Registration failed. Unable to verify the email "+email+" due to "+e.getMessage());
			    }
			    if((Boolean)GameConstants.CONFIGURATION_MAP.get(GameConstants.IS_EMAIL_VERIFICATION_MANDATORY_KEY))
			    return createApiResult(1, "Registration successful. An email verification link "
						+ "has been sent to your email id " + email + " Kindly verify it to access the account. ");
			    else
			    	 return createApiResult(1, "Registration successful. ");
			}
			catch(Exception ex){
				log.info(ex.getMessage());
				return createApiResult(-8, "There was an internal problem registering the user. Unable to save the user in the system");
			}
	}

	private ApiResult validateFieldsInTheSystem(String userName, String email,
			String parentsEmail) {
		User user;
		// username already exists validation
		
		
		if ((user = userService.getUserByName(userName)) != null) {

			log.info("the userName seems to be already registered in the system");
			return createApiResult(-3,
					"the userName seems to be already registered in the system");
		}

		// email already exists validation
		if ((user  = userService.getUserByEmail(email)) != null || (user = userService.getUserByEmail(parentsEmail)) != null  ) {

			log.info("the email address seems to be already registered in the system");
			return createApiResult(-6,
					"the email address seems to be already registered in the system");
		}
		
		// email already exists validation
		if (!StringUtils.isEmpty(parentsEmail) && (user = userService.getUserByParentsEmail(parentsEmail)) != null) {

			log.info("the parent's email address seems to be already registered in the system : "+user.getParentEmailId());
			return createApiResult(-7,
					"the parent's email address seems to be already registered in the system");
		}
		
		return null;
	}

	private ApiResult validateInputFields(String userName, String password,
			String cpassword, String email, String parentsEmail,
			String displayName, String gender) {
		// username already exists validation
		if (!validator.fullNameValidate(displayName)) {

			log.info("your full name, which is your display name, is invalid. it needs to be at least 3 and at most 50 characters");
			return createApiResult(-1,
					"your full name, which is your display name, is invalid. it needs to be at least 3 and at most 50 characters");
		}
		// username already exists validation
		if (gender == null || gender.length() == 0) {

			log.info("no gender selected");
			return createApiResult(-2,
					"no gender selected");
		}
		
		// username format validation
		if (!validator.userNameValidate(userName)) {

		    log.info( "user name should be at least 3 and at most 30 characters");			
			return createApiResult(-3, "user name should be at least 3 and at most 30 characters");
		}
		
		// password format validation
		if (!validator.passwordValidate(password)) {
			 log.info( "invalid password format. Your password must contain 6 to 20 characters");
			return createApiResult(-4,
					"invalid password format. Your password must contain 6 to 20 characters");
		}
		
		// password format validation
		if (!validator.passwordMatchValidate(password,cpassword)) {
			 log.info( "Both passwords should match");
			return createApiResult(-5,
					"Both passwords should match");
		}
		
		// email format validation
		if (!StringUtils.isEmpty(email) && !email.contains("@")) {

			log.info("invalid email format");
			return createApiResult(-6, "invalid email format");
		}
		
		// email format validation
		if (!StringUtils.isEmpty(parentsEmail) && !parentsEmail.contains("@")) {
			log.info("invalid parents email format");
			return createApiResult(-7, "invalid parents email format");
		}
		
		return null;
	}

	private void logAllInputFields(String userName, String email,
			String parentsEmail, String displayName, String gender,
			String facebookId) {
		// validation
		log.info("before validating all fields before registering");
		log.info(userName);		
		log.info(email);
		log.info(parentsEmail);
		log.info(displayName);
		log.info(gender);
		log.info(facebookId);
	}
	
	private void sendWelcomeEmail(String name, String userEmail) throws Exception{
		List<String> recipients = new LinkedList<String>();
		recipients.add(userEmail);
		String emailBody =name +  " Welcome to Lastmanstanding!";
				
		//EmailNotificationSender.sendResetPasswordMail(null, recepients,
			//	emailBody);
		EmailNotificationSender.sendEmailAsync(recipients, emailBody);
		
	}

	private ApiResult createApiResult(int status, String message) {
		ApiResult apr = new ApiResult();
		apr.setStatus(status);
		apr.setMessage(message);
		return apr;
	}

	// TODO: get rid of checks like this. This should be resolved at database
	// level
	private String resolveGender(String gender) {
		return "male".equals(gender) ? "1" : "0";
	}

}
