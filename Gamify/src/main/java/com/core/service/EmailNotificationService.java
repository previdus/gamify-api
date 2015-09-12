package com.core.service;

public interface EmailNotificationService {
	
	public boolean sendAccountActivationEmail(String server, String userEmail) throws Exception;

}
