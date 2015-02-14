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
import com.core.manager.UserManager;
import com.core.service.GenerateDummyDataInDatabase;
import com.core.service.RoomService;
import com.core.service.UserService;
import com.core.util.GenericUtil;

import emailTemplates.EmailNotificationSender;




@Controller
@RequestMapping(value="/api/login")
public class ApiLoginController {
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GenerateDummyDataInDatabase generateDummyDataInDatabase;
	
	@Autowired
	private ApiRegistrationController apiRegistrationController;

	
	@RequestMapping(method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public ApiResult loginPost(
			@RequestParam("userName") String userName, 
			@RequestParam("password") String password
			) 
	{
	    ApiResult apr = new ApiResult();
	    
	    if(userName.equals("mock")){
	    	generateDummyDataInDatabase.generateData();
	    }
	    User userFromRepository = userService.getUser(userName, password);
		if(userFromRepository != null){			
			String userToken = (UserManager.generateUserToken()).toString();
			UserManager.userTokenMap.put(userToken, userFromRepository);
			apr.setMessage("successfully logged in");
			apr.setRedirectLink("/api/rooms/"+userToken);
			// TODO: Set a particular status
			apr.setStatus(1);
			apr.setUser(userFromRepository);
			apr.setUserToken(userToken);
			//response.addHeader("userToken", userToken.toString());
		}		       
		else{
			apr.setMessage("user not found");
			apr.setRedirectLink("");
			// TODO: Set a particular status
			apr.setStatus(-1);			
		}
		return  apr;		    
		
	}
	
	@RequestMapping(value="/facebook",method=RequestMethod.POST,produces="application/json")
	@ResponseBody
	public ApiResult facebookLoginPost(
			@RequestParam("facebookName") String facebookName, 
			@RequestParam("gender") String gender,
			@RequestParam("facebookEmail") String facebookEmail,
			@RequestParam("facebookId") String facebookId
			) 
	{
	    
	    
	   
	    User userFromRepository = userService.getUserByFacebookId(facebookId);
	    if(userFromRepository == null){
		    userFromRepository = userService.getUserByEmail(facebookEmail);
			if(userFromRepository != null){
				userFromRepository.setFacebookId(facebookId);
				userService.saveUser(userFromRepository);
			}
	    }
		if(userFromRepository != null){			
			return loginPost(userFromRepository.getName(),userFromRepository.getPwd());
		}		       
		else{
			String tempPassword = GenericUtil.generateTemporaryPasswordBasedOnUserName(facebookName);
			ApiResult apr = apiRegistrationController.registerPost(facebookEmail, tempPassword, facebookEmail, facebookName, gender, facebookId);
			if(apr.getStatus() == 1){
			    List<String> recepients = new LinkedList<String>();
				recepients.add(facebookEmail);
				String emailBody = "You have been assigned a temporary user id and password to login. You can change these later from the edit profile page of our application Your user Id is " + facebookEmail
						+ ". Your password is "+tempPassword;
			    EmailNotificationSender.sendResetPasswordMail(null, recepients, emailBody);
			    apr.setMessage("You have been registered to our application. Your temporary user id is "+facebookEmail+" and password is "+tempPassword+" An email has already been sent to "
			    		+" the address "+facebookEmail+" with the details. "+apr.getMessage());
			}
			else{
				apr.setMessage("Unable to use your facebook credentials to auto register to our application. " +apr.getMessage());
			}
			return apr;
					
		}
				    
		
	}
	
	
	
	
	
}
