package com.core.dao;

import java.io.Serializable;
import java.util.List;

import com.core.constants.EntityStateENUM;
import com.core.dao.generic.GenericRepository;
import com.core.domain.Question;
import com.core.domain.Test;
import com.core.domain.lms.Topic;

public interface TestDAO extends GenericRepository<Test, Serializable> {	
	

}
