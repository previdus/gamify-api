package com.core.service;


public interface AccountVerificationService {

	public boolean verifyUserEmail(String verificationCode);
	public boolean verifyParentEmail(String userCode, String verificationCode);
	
}
