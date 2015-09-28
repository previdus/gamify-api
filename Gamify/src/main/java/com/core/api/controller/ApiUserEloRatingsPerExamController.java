package com.core.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.UserEloRatingDTO;
import com.core.api.beans.UserEloRatingResult;
import com.core.constants.GameConstants;
import com.core.service.UserEloRatingService;

@Controller
@RequestMapping(value="/api")
public class ApiUserEloRatingsPerExamController {
	
	
	@Autowired
	private UserEloRatingService userEloRatingService;
	
	
	
	@RequestMapping(value="/exam_elo",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public UserEloRatingResult topRatedUsersPerExam(@RequestParam("exam") Long examId) {
		List<UserEloRatingDTO> top = userEloRatingService.getTopUserEloRatingsPerExam(GameConstants.NUM_OF_TOP_PLAYERS_TO_DISPLAY_ON_LEADERBOARD,examId);		
		UserEloRatingResult result = new UserEloRatingResult();
		result.setTopRatedUsers(top);
		return result;
	}
}
