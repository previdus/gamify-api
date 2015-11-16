package com.core.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.ApiResult;
import com.core.constants.GameConstants;
import com.core.domain.User;
import com.core.exception.UserNameNotFoundException;
import com.core.manager.UserManager;
import com.core.service.UserService;
import com.core.validator.GenericValidator;

@Controller
@RequestMapping(value = "/api/profile")
public class ApiProfileController {

	@Autowired
	private UserService userService;

	private GenericValidator validator;

	@Autowired
	public ApiProfileController(GenericValidator validator) {
		this.validator = validator;
	}

	@RequestMapping(value = "/view", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult viewProfile(@RequestParam(value= "userToken", required = false) String userToken, 
			HttpServletRequest request) {
		ApiResult result = new ApiResult();
			result.setUserToken(userToken);
		try {
			long userId = getUserToken(request, userToken);
			User user = userService.getUser(userId);
			result.setStatus(1);
			result.setUser(user);
			return result;
			
		} catch (UserNameNotFoundException e) {
			result.setStatus(-1);
			result.setMessage("Seems you are logged out!");
			return result;
		}
	}

	private long getUserToken(HttpServletRequest request, String userToken)
			throws UserNameNotFoundException {
		User user = UserManager.userTokenMap.get(userToken);
		if (user != null)
			return user.getId();
		ApiResult result = (ApiResult) request.getSession().getAttribute(
				GameConstants.SESSION_VARIABLE_LOGGEDIN_USER_RESULT);
		User loggedInUser = (User) request.getSession().getAttribute(
				GameConstants.SESSION_VARIABLE_LOGGEDIN_USER);
		Long userId = result != null && result.getUser() != null ? result.getUser().getId()
				: null;
		userId = (userId == null || userId == 0) && (loggedInUser != null) ? loggedInUser
				.getId() : userId;
		if (userId != null && userId != 0)
			return userId.longValue();

		throw new UserNameNotFoundException();

	}

	@RequestMapping(value = "/edit/displayName", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult editProfileDisplayName(HttpServletRequest request,
			@RequestParam(value= "userToken", required = false) String userToken,
			@RequestParam(value= "displayName", required = false) String displayName) {

		ApiResult res = new ApiResult();
		res.setUserToken(userToken);
		long userId = 0;
		try {
			userId = getUserToken(request, userToken);
			User user = userService.getUser(userId);
			if (displayName == null || displayName.trim().length() == 0) {
				res.setStatus(-1);
				res.setUser(user);
				res.setMessage("Display name cannot be empty");
				return res;
			}else{
				user.setDisplayName(displayName);
				user = userService.saveUser(user);
				User userInMap = UserManager.userTokenMap.get(userToken);
				if (userInMap != null)
					userInMap.setDisplayName(displayName);
				res.setStatus(1);
				res.setMessage("Successfully updated your display name!");
				res.setUser(user);
				return res;
			}
		} catch (UserNameNotFoundException ex) {
			res.setStatus(-2);
			res.setMessage("Seems you are logged out!");
			return res;
		}

	}

	@RequestMapping(value = "/edit/email", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult editProfileEmail(
			@RequestParam(value= "userToken" , required = false) String userToken,
			@RequestParam(value= "email" , required = false) String email, 
			HttpServletRequest  request) {
		ApiResult apr = new ApiResult();
		apr.setUserToken(userToken);
		
		try{
		long userId = getUserToken(request, userToken);
		User user = userService.getUser(userId);
		if (email == null || !email.contains("@")) {
			apr.setStatus(-1);
			apr.setMessage("invalid email format");
			return apr;
		}else{
			user.setEmailId(email);
			user = userService.saveUser(user);
			User userInMap = UserManager.userTokenMap.get(userToken);
			if (userInMap != null)
				userInMap.setEmailId(email);
			apr.setStatus(1);
			apr.setMessage("Successfully updated your email address!");
			apr.setUser(user);
			return apr;
		}
		}catch(UserNameNotFoundException ex){
			apr.setStatus(-2);
			apr.setMessage("Seems you are logged out!");
			return apr;
		}
	}

	@RequestMapping(value = "/edit/parentEmail", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult editProfileParentsEmail(
			@RequestParam(value = "userToken" , required = false) String userToken,
			@RequestParam(value = "parentEmail" , required = false) String parentEmail, 
			HttpServletRequest  request) {
		ApiResult apr = new ApiResult();
		apr.setUserToken(userToken);
		
		try{
		long userId = getUserToken(request, userToken);
		User user = userService.getUser(userId);
		if (parentEmail == null || !parentEmail.contains("@")) {
			apr.setStatus(-1);
			apr.setMessage("invalid email format");
			return apr;
		}else{
			user.setParentEmailId(parentEmail);
			user = userService.saveUser(user);
			User userInMap = UserManager.userTokenMap.get(userToken);
			if (userInMap != null)
				userInMap.setParentEmailId(parentEmail);
			apr.setStatus(1);
			apr.setMessage("Successfully updated parent email address!");
			apr.setUser(user);
			return apr;
		}
		}catch(UserNameNotFoundException ex){
			apr.setStatus(-2);
			apr.setMessage("Seems you are logged out!");
			return apr;
		}
	}

	
	@RequestMapping(value = "/edit/gender", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult editProfileGender(
			@RequestParam(value = "userToken", required = false) String userToken,
			@RequestParam(value = "gender", required = false) String gender, 
			HttpServletRequest  request) {
		ApiResult apr = new ApiResult();
		apr.setUserToken(userToken);
		
		try{
		long userId = getUserToken(request, userToken);
		User user = userService.getUser(userId);
		if (StringUtils.isBlank(gender)) {
			apr.setStatus(-1);
			apr.setMessage("You must provide a valid gender");
			return apr;
		}else{
			user.setGender(gender);
			user = userService.saveUser(user);
			User userInMap = UserManager.userTokenMap.get(userToken);
			if (userInMap != null)
				userInMap.setGender(gender);
			apr.setStatus(1);
			apr.setMessage("Successfully updated gender!");
			apr.setUser(user);
			return apr;
		}
		}catch(UserNameNotFoundException ex){
			apr.setStatus(-2);
			apr.setMessage("Seems you are logged out!");
			return apr;
		}
	}
	

//	@RequestMapping(value = "/edit/name", method = RequestMethod.POST, produces = "application/json")
//	@ResponseBody
//	public ApiResult editProfileName(
//			@RequestParam("userToken") String userToken,
//			@RequestParam("name") String name, HttpServletRequest request) {
//		ApiResult apr = new ApiResult();
//		apr.setUserToken(userToken);
//		
//		try{
//		long userId = getUserToken(request, userToken);
//		User user = userService.getUser(userId);
//		if (StringUtils.isBlank(name)) {
//			apr.setStatus(-1);
//			apr.setMessage("Your name cannot be empty");
//			return apr;
//		}else{
//			user.setDisplayName(name);
//			user = userService.saveUser(user);
//			User userInMap = UserManager.userTokenMap.get(userToken);
//			if (userInMap != null)
//				userInMap.setDisplayName(name);
//			apr.setStatus(1);
//			apr.setUser(user);
//			return apr;
//		}
//		}catch(UserNameNotFoundException ex){
//			apr.setStatus(-2);
//			apr.setMessage("Seems you are logged out!");
//			return apr;
//		}
//
//	}

	@RequestMapping(value = "/edit/phone", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult editProfilePhone(
			@RequestParam(value = "userToken", required = false) String userToken,
			@RequestParam(value = "phone", required = false) String phone, HttpServletRequest request) {
		ApiResult apr = new ApiResult();
		apr.setUserToken(userToken);
		
		try{
		long userId = getUserToken(request, userToken);
		User user = userService.getUser(userId);
		if (StringUtils.isBlank(phone)) {
			apr.setStatus(-1);
			apr.setMessage("Your phone cannot be empty");
			return apr;
		}else{
			user.setPhoneNo(phone);
			user = userService.saveUser(user);
			User userInMap = UserManager.userTokenMap.get(userToken);
			if (userInMap != null)
				userInMap.setPhoneNo(phone);
			apr.setStatus(1);
			apr.setMessage("Successfully updated phonenumber!");
			apr.setUser(user);
			return apr;
		}
		}catch(UserNameNotFoundException ex){
			apr.setStatus(-2);
			apr.setMessage("Seems you are logged out!");
			return apr;
		}


	}

	@RequestMapping(value = "/edit/pwd", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ApiResult editProfilePassword(
			@RequestParam(value="userToken", required = false) String userToken,
			@RequestParam(value="currentPassword", required = false) String currentPassword,
			@RequestParam(value="newPassword", required = false) String newPassword,
			@RequestParam(value="confirmPassword", required = false) String confirmPassword,
			HttpServletRequest request) {
		ApiResult apr = new ApiResult();
		apr.setUserToken(userToken);
		
		try{
		long userId = getUserToken(request, userToken);
		User user = userService.getUser(userId);
		if (StringUtils.isBlank(currentPassword)) {
			apr.setStatus(-1);
			apr.setMessage("Please provide current passowrd");
			return apr;
		}else if (StringUtils.isBlank(newPassword)) {
			apr.setStatus(-1);
			apr.setMessage("Please provide new passowrd");
			return apr;
		}else if (StringUtils.isBlank(confirmPassword)) {
			apr.setStatus(-1);
			apr.setMessage("Please provide confirm new passowrd");
			return apr;
		}else if (!confirmPassword.equals(newPassword)) {
			apr.setStatus(-1);
			apr.setMessage("Your new password and confirm new password doesn't match!");
			return apr;
		}else if (!currentPassword.equals(user.getPwd())) {
			apr.setStatus(-1);
			apr.setMessage("Your current password is not correct!");
			return apr;
		}
		else{
			user.setPwd(newPassword);
			user = userService.saveUser(user);
			apr.setStatus(1);
			apr.setMessage("Successfully updated password!");
			apr.setUser(user);
			return apr;
		}
		}catch(UserNameNotFoundException ex){
			apr.setStatus(-2);
			apr.setMessage("Seems you are logged out!");
			return apr;
		}
	}
	
	

}
