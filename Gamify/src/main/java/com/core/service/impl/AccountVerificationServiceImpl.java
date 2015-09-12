package com.core.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.constants.UserAccountStatus;
import com.core.dao.EmailVerificationDAO;
import com.core.dao.UserDAO;
import com.core.domain.EmailVerification;
import com.core.domain.User;
import com.core.service.AccountVerificationService;
import com.core.service.OptionService;

@Service("answerKeyService")
public class AccountVerificationServiceImpl implements AccountVerificationService {

	private static final Logger log = LoggerFactory
			.getLogger(AccountVerificationServiceImpl.class);

	@Autowired
	private UserDAO userDAO;// = new AnswerKeyDAOImpl();
	
	@Autowired
	private EmailVerificationDAO emailVerificationDAO;
	
	@Autowired
	private OptionService optionService;

	public boolean verifyEmail(long userId, String verificationCode) {
		
		EmailVerification emailVerification =   emailVerificationDAO.fileByVerificationCode(verificationCode);
		if(emailVerification == null)
			return false;
		User user = userDAO.findObjectById(userId);
		user.setUserAccountStatus(UserAccountStatus.ACTIVE);
		return true;
	}

	
}
