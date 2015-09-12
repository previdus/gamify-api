package com.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.core.constants.UserAccountStatus;
import com.core.dao.UserDAO;
import com.core.domain.User;
import com.core.service.AccountVerificationService;
import com.core.service.OptionService;
import com.core.util.AESCrypto;

@Service("accountVerificationService")
public class AccountVerificationServiceImpl implements AccountVerificationService {

	private static final Logger log = LoggerFactory.getLogger(AccountVerificationServiceImpl.class);

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private OptionService optionService;

	public boolean verifyUserEmail(String verificationCode) {
		String email;
		try{
		  email = AESCrypto.decrypt(verificationCode);
		}catch(Exception ex){
			return false;
		}
		User user = userDAO.getUserByEmail(email);
		if(user == null)
			return false;
		user.setUserAccountStatus(UserAccountStatus.ACTIVE);
		userDAO.saveOrUpdate(user);
		return true;
	}

	public boolean verifyParentEmail(String userCode , String verificationCode) {
		String userEmail;
		String parentsEmail;
		try{
			userEmail = AESCrypto.decrypt(userCode);
			parentsEmail = AESCrypto.decrypt(verificationCode);
		}catch(Exception ex){
			return false;
		}
		User user = userDAO.getUserByEmail(userEmail);
		if(user == null)
			return false;
		if(user.getParentEmailId().trim().equals(parentsEmail)){
		user.setUserAccountStatus(UserAccountStatus.ACTIVE);
		userDAO.saveOrUpdate(user);
		return true;
		}
		return false;
	}

	
}
