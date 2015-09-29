package com.core.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.core.api.beans.ApiResult;
import com.core.domain.User;
import com.core.manager.ExamSectionGameQueueManager;
import com.core.manager.UserManager;
import com.core.service.UserService;

@Controller
@RequestMapping(value = "/api/logout")
public class ApiLogoutController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public ApiResult logoutPost(@RequestParam String userToken) {
		return logout(userToken);
	}

	private ApiResult logout(String userToken) {
		User user = null;
		ApiResult apr = new ApiResult();
		try {

			user = UserManager.userTokenMap.get(userToken);
			if (user != null) {
				ExamSectionGameQueueManager
						.removePlayerFromGameIfQuitOrLoggedOutOrSessionExpired(user);
				UserManager.userTokenMap.remove(userToken);
				apr.setStatus(1);
				apr.setMessage("user successfully logged out");
				apr.setRedirectLink("");
			} else {
				apr.handleNotLoggedInUser();
			}

		} catch (Throwable theException) {

		}
		return apr;
	}

}
