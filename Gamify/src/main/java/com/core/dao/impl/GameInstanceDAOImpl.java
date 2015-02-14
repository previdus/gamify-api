package com.core.dao.impl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.core.dao.GameInstanceDAO;
import com.core.dao.TopicDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.knockout.GameInstance;
import com.core.domain.lms.Topic;

@Repository("gameInstanceDAO")
public class GameInstanceDAOImpl extends HibernateGenericRepository<GameInstance, Serializable> implements GameInstanceDAO{

	
	
	
}
