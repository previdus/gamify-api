package com.core.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.core.dao.EmailVerificationDAO;
import com.core.dao.generic.HibernateGenericRepository;
import com.core.domain.EmailVerification;

@Repository("emailVerificationDAO")
public class EmailVerificationDAOImpl extends 
HibernateGenericRepository<EmailVerification, Serializable> implements
EmailVerificationDAO {

	public EmailVerification fileByVerificationCode(String verificationCode) {
		return findObjectByKey("verificationCode", verificationCode);
	}
	
	

}
