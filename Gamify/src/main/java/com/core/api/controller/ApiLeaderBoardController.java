package com.core.api.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.LeaderBoardResult;
import com.core.api.beans.TotalNumberOfGameWonByAUser;
import com.core.api.beans.UserEloRatingDTO;
import com.core.constants.GameConstants;
import com.core.domain.knockout.UserEloRating;
import com.core.service.LeaderBoardService;
import com.core.service.UserEloRatingService;

@Controller
@RequestMapping(value="/api/leader-board")
public class ApiLeaderBoardController {
	
	@Autowired
	private LeaderBoardService leaderBoardService;
	
	@Autowired
	private UserEloRatingService userEloRatingService;
	
	@RequestMapping(value="/maxwin",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public LeaderBoardResult topUsers() {
		List<TotalNumberOfGameWonByAUser> topFive = leaderBoardService.getTopPersonWhoWonMaxGames(GameConstants.NUM_OF_TOP_PLAYERS_TO_DISPLAY_ON_LEADERBOARD);
		LeaderBoardResult result = new LeaderBoardResult();
		result.setTopUsers(topFive);
		return result;
	}
	
	@RequestMapping(value="/top_elo",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public LeaderBoardResult topRatedUsers() {
		List<UserEloRating> top = userEloRatingService.getTopUserEloRatings(GameConstants.NUM_OF_TOP_PLAYERS_TO_DISPLAY_ON_LEADERBOARD,false);
		List<UserEloRatingDTO> dtoList = createUserEloRatingDTOList(top);
		LeaderBoardResult result = new LeaderBoardResult();
		result.setTopRatedUsers(dtoList);
		return result;
	}
	
	@RequestMapping(value="/top_elo_non_provisional",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public LeaderBoardResult topNonProvisionalRatedUsers() {
		List<UserEloRating> top = userEloRatingService.getTopUserEloRatings(GameConstants.NUM_OF_TOP_PLAYERS_TO_DISPLAY_ON_LEADERBOARD,true);
		List<UserEloRatingDTO> dtoList = createUserEloRatingDTOList(top);
		LeaderBoardResult result = new LeaderBoardResult();
		result.setTopRatedUsers(dtoList);
		return result;
	}

	private List<UserEloRatingDTO> createUserEloRatingDTOList(List<UserEloRating> top) {
		List<UserEloRatingDTO> dtoList = new LinkedList<UserEloRatingDTO>();
		for(UserEloRating uer : top){
			UserEloRatingDTO uerDTO = new UserEloRatingDTO();
			uerDTO.setUserId(uer.getUser().getId());
			uerDTO.setDisplayName(uer.getUser().getDisplayName());
			uerDTO.setNumberOfQuestionsAttempted(uer.getNoOfQuestionsAttempted());
			uerDTO.setEloRating(uer.getEloRating());
			uerDTO.setImageUrl(uer.getUser().getImageUrl());
			dtoList.add(uerDTO);
		}
		
		return dtoList;
		
	}
	
	
	

}
