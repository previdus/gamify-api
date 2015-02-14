package com.core.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.ApiResult;
import com.core.domain.User;
import com.core.manager.UserManager;
import com.core.service.UserService;
import com.core.validator.GenericValidator;

@Controller
@RequestMapping(value="/api/profile")
public class ApiProfileController {

	@Autowired
	private UserService userService;
  
    private GenericValidator validator;
    
    

    @Autowired
	public ApiProfileController(GenericValidator validator) {
    	this.validator = validator;		
	}
	
	@RequestMapping(value="/view", method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public ApiResult viewProfile(
			@RequestParam("userToken") String userToken
			) 
	{
	    ApiResult apr = new ApiResult();   	  
	    apr.setUserToken(userToken);
	    User user = UserManager.userTokenMap.get(userToken);
	    if(user == null){
	    	apr.setStatus(-1);
	    	apr.setMessage("the user does not exist in the system");
	    	return apr;
	    }
	   
	    
	    apr.setStatus(1);
	    apr.setUser(user);
	    return apr;
		
	}
	
	@RequestMapping(value="/edit/displayName", method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public ApiResult editProfileDisplayName(
			@RequestParam("userToken") String userToken,@RequestParam("displayName") String displayName
			) 
	{
	    ApiResult apr = new ApiResult();   	    
	    apr.setUserToken(userToken);
	    User user = UserManager.userTokenMap.get(userToken);
	    if(user == null){
	    	apr.setStatus(-1);
	    	apr.setMessage("the user does not exist in the system");
	    	return apr;
	    }
	   
	    if(displayName != null && displayName.length() >0 && !displayName.equals(user.getDisplayName())){
	    	user.setDisplayName(displayName);
	    	user = userService.saveUser(user);
	    }
	    
	    apr.setStatus(1);
	    apr.setUser(user);
	    
	    return apr;
		
	}
	
	@RequestMapping(value="/edit/email", method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public ApiResult editProfileEmail(
			@RequestParam("userToken") String userToken,@RequestParam("email") String email
			) 
	{
	    ApiResult apr = new ApiResult();   	    
	    apr.setUserToken(userToken);
	    User user = UserManager.userTokenMap.get(userToken);
	    if(user == null){
	    	apr.setStatus(-1);
	    	apr.setMessage("the user does not exist in the system");
	    	return apr;
	    }
	    
	    if(!validator.emailValidate(email)){
	    	apr.setStatus(-1);
	    	apr.setMessage("invalid email format");
	    	return apr;
	    }
	   
	    if(!email.equals(user.getEmailId())){
	    	user.setEmailId(email);
	    	user = userService.saveUser(user);
	    }
	    
	    apr.setStatus(1);
	    apr.setUser(user);
	    
	    return apr;
		
	}
	
	@RequestMapping(value="/edit/gender", method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public ApiResult editProfileGender(
			@RequestParam("userToken") String userToken,@RequestParam("gender") String gender
			) 
	{
	    ApiResult apr = new ApiResult();   	    
	    apr.setUserToken(userToken);
	    User user = UserManager.userTokenMap.get(userToken);
	    if(user == null){
	    	apr.setStatus(-1);
	    	apr.setMessage("the user does not exist in the system");
	    	return apr;
	    }
	   
	    if(gender != null && !gender.equals(user.getGender())){
	    	user.setGender(gender);
	    	user = userService.saveUser(user);
	    }
	    
	    apr.setStatus(1);
	    apr.setUser(user);
	    
	    return apr;
		
	}
	
	@RequestMapping(value="/edit/name", method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public ApiResult editProfileName(
			@RequestParam("userToken") String userToken,@RequestParam("name") String name
			) 
	{
	    ApiResult apr = new ApiResult();   	    
	    apr.setUserToken(userToken);
	    User user = UserManager.userTokenMap.get(userToken);
	    if(user == null){
	    	apr.setStatus(-1);
	    	apr.setMessage("the user does not exist in the system");
	    	return apr;
	    }
	   
	    if(name != null && name.length() > 0 && !name.equals(user.getName())){
	    	user.setName(name);
	    	user = userService.saveUser(user);
	    }
	    
	    apr.setStatus(1);
	    apr.setUser(user);
	    
	    return apr;
		
	}
	
	@RequestMapping(value="/edit/phone", method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public ApiResult editProfilePhone(
			@RequestParam("userToken") String userToken,@RequestParam("phone") String phone
			) 
	{
	    ApiResult apr = new ApiResult();
	    apr.setUserToken(userToken);
	    User user = UserManager.userTokenMap.get(userToken);
	    if(user == null){
	    	apr.setStatus(-1);
	    	apr.setMessage("the user does not exist in the system");
	    	return apr;
	    }
	    if(!validator.phoneNumValidate(phone)){
	    	apr.setStatus(-1);
	    	apr.setMessage("invalid phone number format");
	    	return apr;
	    }
	   
	    if(!phone.equals(user.getPhoneNo())){
	    	user.setPhoneNo(phone);
	    	user = userService.saveUser(user);
	    }
	    
	    apr.setStatus(1);
	    apr.setUser(user);	    
	    return apr;
		
	}
	
	@RequestMapping(value="/edit/pwd", method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public ApiResult editProfilePassword(
			@RequestParam("userToken") String userToken,@RequestParam("pwd") String pwd
			) 
	{
	    ApiResult apr = new ApiResult();
	    apr.setUserToken(userToken);
	    User user = UserManager.userTokenMap.get(userToken);
	    if(user == null){
	    	apr.setStatus(-1);
	    	apr.setMessage("the user does not exist in the system");
	    	return apr;
	    }
	    if(!validator.passwordValidate(pwd)){
	    	apr.setStatus(-1);
	    	apr.setMessage("invalid password format. Your password must contain at least one upper case, one lower case, one digit and at least one of these special characters @#$%");
	    	return apr;
	    }
	   
	    if(!pwd.equals(user.getPwd())){
	    	user.setPwd(pwd);;
	    	user = userService.saveUser(user);
	    }
	    
	    apr.setStatus(1);
	    apr.setUser(user);	    
	    return apr;		
	}
	
	
	
	
	
}
