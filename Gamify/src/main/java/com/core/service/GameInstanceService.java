package com.core.service;

import com.core.domain.knockout.GameInstance;

public interface GameInstanceService {

	public GameInstance saveOrUpdate(GameInstance gi);

	public GameInstance getGameInstance(long gameId);

}
