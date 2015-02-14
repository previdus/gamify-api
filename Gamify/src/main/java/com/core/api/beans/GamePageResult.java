package com.core.api.beans;

import com.core.domain.knockout.GameInstance;

public class GamePageResult extends ApiResult {

	private GameInstance gi;

	public GameInstance getGi() {
		return gi;
	}

	public void setGi(GameInstance gi) {
		this.gi = gi;
	}
}
