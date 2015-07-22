package com.core.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.LeaderBoardResult;
import com.core.api.beans.TotalNumberOfGameWonByAUser;
import com.core.service.LeaderBoardService;

@Controller
@RequestMapping(value="/api/leader-board")
public class ApiLeaderBoardController {
	
	@Autowired
	private LeaderBoardService leaderBoardService;
	
	@RequestMapping(value="/maxwin",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public LeaderBoardResult topUsers() {
		List<TotalNumberOfGameWonByAUser> topFive = leaderBoardService.getTopFivePersonWhoWonMaxGames();
		LeaderBoardResult result = new LeaderBoardResult();
		result.setTopUsers(topFive);
		return result;
	}

}
