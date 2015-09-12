package com.core.dao;

import java.io.Serializable;

import com.core.dao.generic.GenericRepository;
import com.core.domain.EmailVerification;

public interface EmailVerificationDAO extends GenericRepository<EmailVerification, Serializable>{
	
	
	 public EmailVerification fileByVerificationCode(String verificationCode);
	

}
