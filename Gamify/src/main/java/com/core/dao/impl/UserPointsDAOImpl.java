package com.core.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.core.dao.UserPointsDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.knockout.UserPoints;

@Repository("userPointsDAO")
public class UserPointsDAOImpl extends HibernateGenericRepository<UserPoints, Serializable>
		implements UserPointsDAO {
	

		


}
