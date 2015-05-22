package com.core.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.core.domain.User;

public class UserManager {

	public static Map<String, User> userTokenMap = new HashMap<String, User>();

	private static Random random = new Random();

	public static String generateUserToken() {
		return new Long(random.nextLong()).toString();
	}

}
