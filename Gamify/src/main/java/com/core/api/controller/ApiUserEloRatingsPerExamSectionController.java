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
import com.core.domain.knockout.UserEloRatingPerExamSection;
import com.core.service.UserEloRatingService;

@Controller
@RequestMapping(value="/api")
public class ApiUserEloRatingsPerExamSectionController {
	
	
	@Autowired
	private UserEloRatingService userEloRatingService;
	
	
	
	@RequestMapping(value="/exam_section_elo",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public UserEloRatingResult topRatedUsersPerExamSection(@RequestParam("examSection") Long examSectionId) {
		return returnRatingResultForExamSection(examSectionId, false);
	}

	private UserEloRatingResult returnRatingResultForExamSection(Long examSectionId, boolean excludeProvisional) {
		List<UserEloRatingPerExamSection> top = userEloRatingService.getTopUserEloRatingsPerExamSection(GameConstants.NUM_OF_TOP_PLAYERS_TO_DISPLAY_ON_LEADERBOARD,examSectionId, excludeProvisional);
		List<UserEloRatingDTO> dtoList = createUserEloRatingDTOListPerExamSection(top);
		UserEloRatingResult result = new UserEloRatingResult();
		result.setTopRatedUsers(dtoList);
		return result;
	}
	
	@RequestMapping(value="/exam_section_elo_non_provisional",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public UserEloRatingResult topNonProvisionalRatedUsersPerExamSection(@RequestParam("examSection") Long examSectionId) {
		return returnRatingResultForExamSection(examSectionId, true);
	}

	private List<UserEloRatingDTO> createUserEloRatingDTOListPerExamSection(List<UserEloRatingPerExamSection> top) {
		List<UserEloRatingDTO> dtoList = new LinkedList<UserEloRatingDTO>();
		for(UserEloRatingPerExamSection uer : top){
			UserEloRatingDTO uerDTO = new UserEloRatingDTO();
			uerDTO.setUserId(uer.getUserExamSection().getUser().getId());
			uerDTO.setDisplayName(uer.getUserExamSection().getUser().getDisplayName());
			uerDTO.setNumberOfQuestionsAttempted(uer.getNoOfQuestionsAttempted());
			uerDTO.setEloRating(uer.getEloRating());
			uerDTO.setImageUrl(uer.getUserExamSection().getUser().getImageUrl());
			dtoList.add(uerDTO);
		}
		
		return dtoList;
		
	}
	
	
	
	

}
