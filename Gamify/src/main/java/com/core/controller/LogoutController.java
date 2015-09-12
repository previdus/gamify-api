package com.core.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.core.constants.GameConstants;
import com.core.domain.User;
import com.core.manager.GameQueueManager;
import com.core.service.UserService;

@Controller
@RequestMapping(value = "/logout")
public class LogoutController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView logout(Model model, HttpServletRequest request) {
		User user = null;
		try {
			user = (User) request.getSession().getAttribute(
					GameConstants.SESSION_VARIABLE_LOGGEDIN_USER);
			GameQueueManager
					.removePlayerFromGameIfQuitOrLoggedOutOrSessionExpired(user);
			request.getSession().removeAttribute(
					GameConstants.SESSION_VARIABLE_LOGGEDIN_USER);
			request.getSession().removeAttribute(
					GameConstants.SESSION_VARIABLE_LOGGEDIN_USER_RESULT);

		} catch (Throwable theException) {

		}
		model.addAttribute(new User());
		return new ModelAndView("account/LoginPage");
	}

}
