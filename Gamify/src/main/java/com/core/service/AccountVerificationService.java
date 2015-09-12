package com.core.service;


public interface AccountVerificationService {

	public boolean verifyEmail(long userId, String verificationCode);


}
