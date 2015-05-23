package com.core.dao.impl;

import java.io.Serializable;
import org.springframework.stereotype.Repository;
import com.core.dao.GameInstanceDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.knockout.GameInstance;

@Repository("gameInstanceDAO")
public class GameInstanceDAOImpl extends
		HibernateGenericRepository<GameInstance, Serializable> implements
		GameInstanceDAO {

}
