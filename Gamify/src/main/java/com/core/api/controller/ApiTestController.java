package com.core.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.api.beans.ApiTestResult;
import com.core.domain.Test;
import com.core.service.TestService;

@Controller
@RequestMapping(value = "/api/test")
public class ApiTestController {
	
	private static final Logger log = LoggerFactory.getLogger(ApiTestController.class);

	@Autowired
	private TestService testService;

	
	
	
	@RequestMapping(value="/load", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ApiTestResult loadTest(@RequestParam("testId") Long testId,
			HttpServletRequest request) throws Exception {
		ApiTestResult apr = new ApiTestResult();
		Test test = testService.getTest(testId);
		apr.setTest(test);
		return apr;
	}

	

}
