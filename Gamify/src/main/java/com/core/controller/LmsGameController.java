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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.core.api.beans.ApiResult;
import com.core.api.beans.GamePageResult;
import com.core.api.controller.ApiLmsGameController;
import com.core.constants.GameConstants;
import com.core.domain.User;
import com.core.domain.knockout.GameInstance;
import com.core.manager.CommonQueueManager;
import com.core.manager.ExamSectionGameQueueManager;
import com.core.manager.TopicGameQueueManager;
import com.core.service.ExamSectionService;
import com.core.service.RoomService;

@Controller
@RequestMapping(value = "/play")
public class LmsGameController {
	private static final Logger log = LoggerFactory
			.getLogger(LmsGameController.class);
	@Autowired
	private RoomService roomService;
	@Autowired
	private ExamSectionService examSectionService;

	private Validator validator;

	@Autowired
	private ApiLmsGameController apiLmsGameController;

	@Autowired
	public LmsGameController(Validator validator) {
		this.validator = validator;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView show(Model model) {
		model.addAttribute(roomService.getRoom());
		return new ModelAndView("account/rooms");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/pollGame", produces = "application/json")
	public @ResponseBody GameInstance pollGameInstance(
			@RequestParam("userId") String userId, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		ApiResult result = (ApiResult) request.getSession().getAttribute(
				GameConstants.SESSION_VARIABLE_LOGGEDIN_USER_RESULT);
		GameInstance gi = null;
		if (userId != null & userId.length() > 0 && !userId.equals("undefined")
				&& result != null) {
			GamePageResult gamePageResult = apiLmsGameController
					.pollGameInstance(result.getUserToken());
			if (gamePageResult != null) {
				gi = gamePageResult.getGi();
				return gi;
			}
			
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/respondToQuestion", produces = "application/json")
	public @ResponseBody GameInstance respondToQuestion(
			@RequestParam("userId") String userId,
			@RequestParam("questionId") String questionId,
			@RequestParam("optionId") String optionId,
			@RequestParam(value="freeResponseText", required = false) String answer,
			@RequestParam("timeTakenToRespond") String timeTakenToRespond,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		log.info("In respondToQuestion");
		ApiResult result1 = (ApiResult) request.getSession().getAttribute(
				GameConstants.SESSION_VARIABLE_LOGGEDIN_USER_RESULT);
	
		if (result1 != null) {
			GamePageResult result = apiLmsGameController.respondToQuestion(
					result1.getUserToken(), questionId, optionId,answer,
					timeTakenToRespond);
			if (result != null)
				return result.getGi();
		}
		return null;
		
	}

	@RequestMapping(method = RequestMethod.POST, value="/examSection")
	public ModelAndView selectExamSectionRoom(
			@RequestParam("examSection") String examSection,
			HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		

		GameInstance gi = null;
		if (examSection != null && examSection.length() > 0) {

			User user = (User) request.getSession().getAttribute(
					GameConstants.SESSION_VARIABLE_LOGGEDIN_USER);
			ApiResult result = (ApiResult) request.getSession().getAttribute(
					GameConstants.SESSION_VARIABLE_LOGGEDIN_USER_RESULT);
			if (user != null) {
				if (ExamSectionGameQueueManager.checkIfPlayerAlreadyInGame(user)) {
					ExamSectionGameQueueManager
							.removePlayerFromGameIfQuitOrLoggedOutOrSessionExpired(user);
					model.addAttribute(roomService.getRoom());
					return new ModelAndView("account/rooms");

				} else {
					if(result == null){
						response.sendRedirect("login");
						return null;
					}
					GamePageResult gpr = apiLmsGameController.selectExamSectionRoom(
							result.getUserToken(), examSection);
				
					ModelAndView mav = new ModelAndView("account/lmsgame");
					mav.addObject("userId", user.getId());
					mav.addObject("gameId", gpr.getGi().getId());
					
					return mav;
				}

			}
		}

		ModelAndView mav = new ModelAndView("account/LoginPage");
		mav.addObject(new User());
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/topic")
	public ModelAndView selectTopicRoom(
			@RequestParam("topic") String topicId,
			HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		

		GameInstance gi = null;
		if (topicId != null && topicId.length() > 0) {

			User user = (User) request.getSession().getAttribute(
					GameConstants.SESSION_VARIABLE_LOGGEDIN_USER);
			ApiResult result = (ApiResult) request.getSession().getAttribute(
					GameConstants.SESSION_VARIABLE_LOGGEDIN_USER_RESULT);
			if (user != null) {
				if (CommonQueueManager.checkIfPlayerAlreadyInGame(user)) {
					CommonQueueManager
							.removePlayerFromGameIfQuitOrLoggedOutOrSessionExpired(user);
					model.addAttribute(roomService.getRoom());
					return new ModelAndView("account/rooms");

				} else {
					if(result == null){
						response.sendRedirect("login");
						return null;
					}
					GamePageResult gpr = apiLmsGameController.selectTopicRoom(
							result.getUserToken(), topicId);
				
					ModelAndView mav = new ModelAndView("account/lmsgame");
					mav.addObject("userId", user.getId());
					mav.addObject("gameId", gpr.getGi().getId());
					
					return mav;
				}

			}
		}

		ModelAndView mav = new ModelAndView("account/LoginPage");
		mav.addObject(new User());
		return mav;
	}

}
