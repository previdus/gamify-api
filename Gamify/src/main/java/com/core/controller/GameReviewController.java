package com.core.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.core.api.beans.ApiResult;
import com.core.api.beans.GamePageResult;
import com.core.api.controller.ApiLmsGameController;
import com.core.constants.GameConstants;
import com.core.domain.knockout.GameInstance;
import com.core.service.GameInstanceService;

@Controller
@RequestMapping(value = "/review-game")
public class GameReviewController {
	
	@Autowired
	private ApiLmsGameController apiLmsGameController;

	@Autowired
	private GameInstanceService gameInstanceService;

	@RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
	public ModelAndView reviewGame( 
			@PathVariable("gameId") long gameId,
			Model model, HttpServletRequest request) throws IOException {
//		ApiResult apiResult = (ApiResult) request.getSession().getAttribute(
//				GameConstants.SESSION_VARIABLE_LOGGEDIN_USER_RESULT);
//		GamePageResult gamePageResult = apiLmsGameController.pollGameInstance(apiResult.getUserToken());
//		GameInstance gi = gamePageResult.getGi();
//		if(gi == null)
		GameInstance	gi = gameInstanceService.getGameInstance(gameId);
		return new ModelAndView("account/game-review", "gi", gi);
	}
}
