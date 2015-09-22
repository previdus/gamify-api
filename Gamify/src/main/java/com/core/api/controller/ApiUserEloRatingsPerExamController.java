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
import com.core.domain.knockout.UserEloRatingPerExam;
import com.core.service.UserEloRatingService;

@Controller
@RequestMapping(value="/api")
public class ApiUserEloRatingsPerExamController {
	
	
	@Autowired
	private UserEloRatingService userEloRatingService;
	
	
	
	@RequestMapping(value="/exam_elo",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public UserEloRatingResult topRatedUsersPerExam(@RequestParam("exam") Long examId) {
		return returnRatingResultForExam(examId, false);
	}

	private UserEloRatingResult returnRatingResultForExam(Long examId, boolean excludeProvisional) {
		List<UserEloRatingPerExam> top = userEloRatingService.getTopUserEloRatingsPerExam(GameConstants.NUM_OF_TOP_PLAYERS_TO_DISPLAY_ON_LEADERBOARD,examId, excludeProvisional);
		List<UserEloRatingDTO> dtoList = createUserEloRatingDTOListPerExam(top);
		UserEloRatingResult result = new UserEloRatingResult();
		result.setTopRatedUsers(dtoList);
		return result;
	}
	
	@RequestMapping(value="/exam_elo_non_provisional",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public UserEloRatingResult topNonProvisionalRatedUsersPerExam(@RequestParam("exam") Long examId) {
		return returnRatingResultForExam(examId, true);
	}

	private List<UserEloRatingDTO> createUserEloRatingDTOListPerExam(List<UserEloRatingPerExam> top) {
		List<UserEloRatingDTO> dtoList = new LinkedList<UserEloRatingDTO>();
		for(UserEloRatingPerExam uer : top){
			UserEloRatingDTO uerDTO = new UserEloRatingDTO();
			uerDTO.setUserId(uer.getUserExam().getUser().getId());
			uerDTO.setDisplayName(uer.getUserExam().getUser().getDisplayName());
			uerDTO.setNumberOfQuestionsAttempted(uer.getNoOfQuestionsAttempted());
			uerDTO.setEloRating(uer.getEloRating());
			uerDTO.setImageUrl(uer.getUserExam().getUser().getImageUrl());
			dtoList.add(uerDTO);
		}
		
		return dtoList;
		
	}
	
	
	
	

}
