package com.core.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.LeaderBoardPointBasedResult;
import com.core.api.beans.LeaderBoardResult;
import com.core.api.beans.TotalNoOfPointsScoredByUser;
import com.core.api.beans.TotalNumberOfGameWonByAUser;
import com.core.constants.GameConstants;
import com.core.service.LeaderBoardService;

@Controller
@RequestMapping(value="/api/leader-board")
public class ApiLeaderBoardController {
	
	@Autowired
	private LeaderBoardService leaderBoardService;

	@RequestMapping(value="/maxwin",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public LeaderBoardResult topUsers() {
		List<TotalNumberOfGameWonByAUser> topFive = leaderBoardService.getTopPersonWhoWonMaxGames((Integer)GameConstants.CONFIGURATION_MAP.get(GameConstants.NUM_OF_TOP_PLAYERS_TO_DISPLAY_ON_LEADERBOARD_KEY));
		LeaderBoardResult result = new LeaderBoardResult();
		result.setTopUsers(topFive);
		return result;
	}
	
	
	@RequestMapping(value="/maxpoints/{topicId}",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public LeaderBoardPointBasedResult topUsersByPoints(@PathVariable("topicId") Long topicId, 
			Model model, HttpServletRequest request) {
		List<TotalNoOfPointsScoredByUser> topScorers = leaderBoardService.getTopPersonsWhoScoredMaxPointsInLMS(topicId);
		LeaderBoardPointBasedResult result = new LeaderBoardPointBasedResult();
		result.setTotalNoOfPointsScoredByUsers(topScorers);
		result.setTopicId(topicId);
		return result;
	}
	
}
