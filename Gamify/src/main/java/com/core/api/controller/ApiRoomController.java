package com.core.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.core.api.beans.RoomPageResult;
import com.core.domain.User;
import com.core.domain.lms.Room;
import com.core.manager.ExamSectionGameQueueManager;
import com.core.manager.UserManager;
import com.core.service.RoomService;

@Controller
@RequestMapping(value = "/api/rooms")
public class ApiRoomController {

	@Autowired
	private RoomService roomService;

	@RequestMapping(method = RequestMethod.GET, value = "/{userToken}", produces = "application/json")
	@ResponseBody
	public RoomPageResult show(@PathVariable("userToken") String userToken) {
		RoomPageResult rpr = new RoomPageResult();
		rpr.setUserToken(userToken);
		User user = UserManager.userTokenMap.get(userToken);
		if (user != null) {
			Room room = roomService.getRoom();
			room.setRoomName("Main Room");
			rpr.setRoom(room);
			rpr.setRedirectLink("");
		} else {
			rpr.handleNotLoggedInUser();
		}
		return rpr;
	}

	@RequestMapping(value = "/changeroom/{userToken}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public RoomPageResult changeRoom(@PathVariable("userToken") String userToken) {

		RoomPageResult rpr = new RoomPageResult();
		rpr.setUserToken(userToken);
		User user = UserManager.userTokenMap.get(userToken);
		if (user != null) {
			ExamSectionGameQueueManager
					.removePlayerFromGameIfQuitOrLoggedOutOrSessionExpired(user);
			rpr.setRedirectLink("/api/rooms/" + userToken);
		} else {
			rpr.handleNotLoggedInUser();
		}
		return rpr;
	}

}
