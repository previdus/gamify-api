package com.core.api.beans;

import com.core.domain.knockout.GameInstance;

public class GameReviewResult extends ApiResult{
	
	private GameInstance gameInstance;

	public GameReviewResult(GameInstance gameInstance) {
		super();
		this.gameInstance = gameInstance;
	}

	public GameInstance getGameInstance() {
		return gameInstance;
	}

	public void setGameInstance(GameInstance gameInstance) {
		this.gameInstance = gameInstance;
	}

}
