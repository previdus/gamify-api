package com.core.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.core.constants.EntityStateENUM;
import com.core.dao.QuestionDAO;
import com.core.dao.TestDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.Question;
import com.core.domain.Test;
import com.core.domain.lms.Topic;

@Repository("testDAO")
public class TestDAOImpl extends
		HibernateGenericRepository<Test, Serializable> implements
		TestDAO {

}
