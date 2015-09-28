package com.core.api.controller;

import java.util.LinkedList;
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
import com.core.domain.knockout.UserEloRatingPerTopic;
import com.core.service.UserEloRatingService;

@Controller
@RequestMapping(value="/api")
public class ApiUserEloRatingsPerTopicController {
	
	
	@Autowired
	private UserEloRatingService userEloRatingService;
	
	
	
	@RequestMapping(value="/topic_elo",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public UserEloRatingResult topRatedUsersperTopicm(@RequestParam("topic") Long topicId) {
		return returnRatingResultForTopic(topicId, false);
	}

	private UserEloRatingResult returnRatingResultForTopic(Long topicId, boolean excludeProvisional) {
		List<UserEloRatingPerTopic> top = userEloRatingService.getTopUserEloRatingsPerTopic(GameConstants.NUM_OF_TOP_PLAYERS_TO_DISPLAY_ON_LEADERBOARD,topicId, excludeProvisional);
		List<UserEloRatingDTO> dtoList = createUserEloRatingDTOListPerTopic(top);
		UserEloRatingResult result = new UserEloRatingResult();
		result.setTopRatedUsers(dtoList);
		return result;
	}
	
	@RequestMapping(value="/topic_elo_non_provisional",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public UserEloRatingResult topNonProvisionalRatedUsersPerTopic(@RequestParam("topic") Long topicId) {
		return returnRatingResultForTopic(topicId, true);
	}

	private List<UserEloRatingDTO> createUserEloRatingDTOListPerTopic(List<UserEloRatingPerTopic> top) {
		List<UserEloRatingDTO> dtoList = new LinkedList<UserEloRatingDTO>();
		int index = 1;
		for(UserEloRatingPerTopic uer : top){
			UserEloRatingDTO uerDTO = new UserEloRatingDTO();
			uerDTO.setUserId(uer.getUserTopic().getUser().getId());
			uerDTO.setDisplayName(uer.getUserTopic().getUser().getDisplayName());
			uerDTO.setNumberOfQuestionsAttempted(uer.getNoOfQuestionsAttempted());
			uerDTO.setEloRating(uer.getEloRating());
			uerDTO.setImageUrl(uer.getUserTopic().getUser().getImageUrl());
			double percentile = (top.size() - index)*100/top.size();
			uerDTO.setPercentile(percentile);
			index++;
			dtoList.add(uerDTO);
		}
		
		return dtoList;
		
	}
	
	
	
	

}
