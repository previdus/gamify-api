package com.core.api.beans;

import com.core.domain.Test;



public class ApiTestResult extends ApiResult {

	private Test test;

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}
}
