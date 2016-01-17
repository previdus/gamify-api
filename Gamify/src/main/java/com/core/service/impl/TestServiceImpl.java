package com.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.dao.TestDAO;
import com.core.domain.Test;
import com.core.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService{
	
	@Autowired
	private TestDAO testDAO;
	
	public Test getTest(Long testId){
		return testDAO.findObjectById(testId);
	}

}
