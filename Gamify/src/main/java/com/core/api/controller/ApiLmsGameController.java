package com.core.api.controller;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.core.api.beans.GamePageResult;
import com.core.domain.User;
import com.core.domain.knockout.GameInstance;
import com.core.domain.lms.ExamSection;
import com.core.manager.GameQueueManager;
import com.core.manager.UserManager;
import com.core.service.ExamSectionService;
import com.core.service.RoomService;

@Controller
@RequestMapping(value = "/api/play")
public class ApiLmsGameController {
	private static final Logger log = LoggerFactory
			.getLogger(ApiLmsGameController.class);
	@Autowired
	private RoomService roomService;
	@Autowired
	private ExamSectionService examSectionService;

	@RequestMapping(value = "/{userToken}/{examSection}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public GamePageResult selectRoom(
			@PathVariable("userToken") String userToken,
			@PathVariable("examSection") String examSection) {

		GameInstance gi = null;
		GamePageResult gp = new GamePageResult();
		gp.setUserToken(userToken);
		if (examSection != null && examSection.length() > 0) {

			User user = UserManager.userTokenMap.get(userToken);

			if (user != null) {
				if (GameQueueManager.checkIfPlayerAlreadyInGame(user)) {
					GameQueueManager
							.removePlayerFromGameIfQuitOrLoggedOutOrSessionExpired(user);
					gp.setStatus(0);
					gp.setMessage("Seems you are already in this game. Redirecting you to the main room");
					// redirect to main room
					gp.setRedirectLink("");
					gp.setUser(user);

				} else {
					ExamSection es = examSectionService
							.getExamSection(new Long(examSection));
					if (es != null) {
						gi = GameQueueManager.createGameInstance(es, user);
						gp.setGi(gi);
						gp.setStatus(1);
						gp.setMessage("Success");
						// redirect link is blank in this case

					} else {
						gp.setStatus(-1);
						gp.setMessage("Invalid examSection specified");
					}
					gp.setRedirectLink("");
					gp.setUser(user);
				}

			} else {
				gp.handleNotLoggedInUser();
			}
		}
		return gp;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/pollGame/{userToken}", produces = "application/json")
	@ResponseBody
	public GamePageResult pollGameInstance(
			@PathVariable("userToken") String userToken) throws IOException {
		GameInstance gi = null;
		GamePageResult gp = new GamePageResult();
		gp.setUserToken(userToken);
		User user = UserManager.userTokenMap.get(userToken);
		if (user != null) {

			gi = GameQueueManager.getGameInstanceForPlayer(user.getId());
			if (gi != null) {
				if (gi.getCurrentQuestion() != null)
					log.debug("----- Current Question Id is : "
							+ gi.getCurrentQuestion().getId());

				gi.incrementPollCountForPlayer(user.getId());
				gp.setStatus(1);
				gp.setUser(user);

				gp.setMessage("successfully polled and retrieved the game instance");
				;
				gp.setGi(gi);

			} else {
				log.info("Pool Got GI as Null ----------------------------------------------------------------------");
			}

		} else {
			gp.handleNotLoggedInUser();

		}

		return gp;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/respondToQuestion/{userToken}/{questionId}/{optionId}/{timeTakenToRespond}", produces = "application/json")
	@ResponseBody
	public GamePageResult respondToQuestion(
			@PathVariable("userToken") String userToken,
			@PathVariable("questionId") String questionId,
			@PathVariable("optionId") String optionId,
			@PathVariable("timeTakenToRespond") String timeTakenToRespond)
			throws IOException {
		log.info("In respondToQuestion");
		User user = UserManager.userTokenMap.get(userToken);
		GamePageResult gp = new GamePageResult();
		gp.setUserToken(userToken);
		if (user != null) {
			GameInstance gi = GameQueueManager.recordPlayerResponseToQuestion(
					user.getId(), new Long(questionId), new Long(optionId),
					new Long(timeTakenToRespond));
			if (gi == null) {
				log.info("In respondToQuestion - GI is NUll --------------------------------------------------------------------------");
				// handleWhenGameInstanceIsEmptyForUser(gp, userToken);
			} else {
				gp.setGi(gi);
				gp.setUser(user);
				gp.setMessage("successfully recorded user's response");
				gp.setRedirectLink("");
				gp.setStatus(1);
			}

		} else {
			gp.handleNotLoggedInUser();
		}

		return gp;
	}

}
