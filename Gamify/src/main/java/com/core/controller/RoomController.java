package com.core.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.core.api.controller.ApiRegistrationController;
import com.core.constants.GameConstants;
import com.core.domain.User;
import com.core.manager.ExamSectionGameQueueManager;
import com.core.service.RoomService;

@Controller
@RequestMapping(value = "/rooms")
public class RoomController {
	

	private static final Logger log = LoggerFactory
			.getLogger(RoomController.class);

	@Autowired
	private RoomService roomService;

	private Validator validator;

	@Autowired
	public RoomController(Validator validator) {
		this.validator = validator;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView show(Model model, HttpServletRequest request) {
		roomService.getRoom().setRoomName("Main Room");
		log.info("in /rooms get request");
		model.addAttribute(
				"user",
				(User) request.getSession().getAttribute(
						GameConstants.SESSION_VARIABLE_LOGGEDIN_USER));
		model.addAttribute("room", roomService.getRoom());
		return new ModelAndView("account/rooms");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/changeroom")
	public String changeRoom(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		roomService.getRoom().setRoomName("Main Room");
		User user = (User) request.getSession().getAttribute(
				GameConstants.SESSION_VARIABLE_LOGGEDIN_USER);
		ExamSectionGameQueueManager
				.removePlayerFromGameIfQuitOrLoggedOutOrSessionExpired(user);
		return "redirect:/rooms";
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView selectRoom(Long roomId) {

		return null;
	}

}
