package com.core.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.LeaderBoardResult;
import com.core.service.impl.LeaderBoardServiceImpl;

@Controller
@RequestMapping(value="/api/leader-board")
public class ApiLeaderBoardController {
	
	@Autowired
	LeaderBoardServiceImpl leaderBoardService;
	
	@RequestMapping(value="/maxwin",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public LeaderBoardResult topUsers() {
		return new LeaderBoardResult(leaderBoardService.getTopFivePersonWhoWonMaxGames());
	}

}
