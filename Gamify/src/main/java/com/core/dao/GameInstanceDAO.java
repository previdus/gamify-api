package com.core.dao;

import java.io.Serializable;
import com.core.dao.generic.GenericRepository;
import com.core.domain.knockout.GameInstance;

public interface GameInstanceDAO extends
		GenericRepository<GameInstance, Serializable> {

}
