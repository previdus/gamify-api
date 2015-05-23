package com.core.dao.impl;

import java.io.Serializable;
import org.springframework.stereotype.Repository;
import com.core.dao.TopicDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.lms.Topic;

@Repository("topicDAO")
public class TopicDAOImpl extends
		HibernateGenericRepository<Topic, Serializable> implements TopicDAO {

}
