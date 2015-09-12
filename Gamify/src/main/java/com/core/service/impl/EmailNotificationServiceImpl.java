package com.core.service.impl;

import java.util.LinkedList;
import org.springframework.stereotype.Service;
import com.core.service.EmailNotificationService;
import com.core.util.AESCrypto;
import com.core.util.Emailer;

@Service("emailNotificationService")
public class EmailNotificationServiceImpl implements EmailNotificationService{
 
	private static String EMAIL_ACCOUNT_VERIFICATION_EMAIL_SUBJECT = "Email Verification needed";
	private static String USER_EMAIL_VERIFICATION_PATH = "/verify-user-email/";
	
	public boolean sendAccountActivationEmail(String server, String userEmail) throws Exception {
		StringBuffer emailBody = new StringBuffer("Thankyou for registering with Lastmastanding! Please click on the below email vefirication link to activate your account!");
		emailBody.append(server);
		emailBody.append(USER_EMAIL_VERIFICATION_PATH);
		emailBody.append(AESCrypto.encrypt(userEmail));
		LinkedList<String> recipients = new LinkedList<String>();
		recipients.add(userEmail);
		new Emailer(null, null,EMAIL_ACCOUNT_VERIFICATION_EMAIL_SUBJECT ,emailBody.toString() , recipients);
		return true;
	}

}
