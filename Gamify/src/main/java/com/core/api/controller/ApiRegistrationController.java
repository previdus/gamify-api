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
@RequestMapping(value="/api/register")
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
	
	@RequestMapping(method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public ApiResult registerPost(
			@RequestParam("userName") String userName, 
			@RequestParam("password") String password,
			@RequestParam("email") String email,
			@RequestParam("displayName") String displayName,
			@RequestParam("gender") String gender,
			@RequestParam("facebookId") String facebookId
			) 
	{
	    ApiResult apr = new ApiResult();   
	    //validation
	    //email format validation
	    if(!validator.emailValidate(email)){
	    	apr.setStatus(-1);
	    	apr.setMessage("invalid email format");
	    	return apr;
	    }
	    //username format validation
	    if(!validator.userNameValidate(userName)){
	    	apr.setStatus(-1);
	    	apr.setMessage("invalid userName format");
	    	return apr;
	    }
	    
	    //password format validation
	    if(!validator.passwordValidate(password)){
	    	apr.setStatus(-1);
	    	apr.setMessage("iinvalid password format. Your password must contain 6 to 20 characters");
	    	return apr;
	    }
	    //email already exists validation
	   
	    if(userService.getUserByEmail(email) != null){
	    	apr.setStatus(-1);
	    	apr.setMessage("the email address seems to be already registered in the system");
	    	return apr;
	    }
	    //username already exists validation
	    if(userService.getUserByName(userName) != null){
	    	apr.setStatus(-1);
	    	apr.setMessage("the userName seems to be already registered in the system");
	    	return apr;
	    }   
	    //end validation
	    
	    User user = new User(userName,password,email,displayName, gender, facebookId,GenericUtil.generateFacebookProfileSmallImageUrl(facebookId));
	    user = userService.saveUser(user);
        //null check
	    if(user == null){
	    	apr.setStatus(-1);
	    	apr.setMessage("there was a problem registering the user");
	    	return apr;
	    }
	    //successful registration so autologin
	    apr =  apiLoginController.loginPost(userName, password);
	    apr.setMessage("Registration successful. "+ apr.getMessage());
	    return apr;
		
	}	
	
	
	
	
}
