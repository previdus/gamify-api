package com.core.api.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.GamePageResult;
import com.core.api.beans.GameReviewResult;
import com.core.domain.knockout.GameInstance;
import com.core.service.GameInstanceService;

@Controller
@RequestMapping(value = "/api/review-game")
public class ApiGameReviewController {
	
	@Autowired
	private ApiLmsGameController apiLmsGameController;

	@Autowired
	private GameInstanceService gameInstanceService;

	@RequestMapping(value = "/{userToken}/{gameId}", method = RequestMethod.GET,produces="application/json")
	@ResponseBody
	public GameReviewResult reviewGame( 
			@PathVariable("gameId") long gameId,
			@PathVariable("userToken") String userToken,
			Model model, HttpServletRequest request) throws IOException {
		GamePageResult gamePageResult = apiLmsGameController.pollGameInstance(userToken);
		GameInstance gi = gamePageResult.getGi();
		
		if(gi == null)
			gi = gameInstanceService.getGameInstance(gameId);
		
		GameReviewResult result = new GameReviewResult(gi);
		result.setStatus(1);
		result.setUserToken(userToken);
		return result;
	}

}
