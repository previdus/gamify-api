package com.core.util;

public class GenericUtil {

	private static String facebookProfileImageUrl = "http://graph.facebook.com/FACEBOOKID/picture?type=small";

	public static String generateTemporaryPasswordBasedOnUserName(
			String userName) {

		return userName.replaceAll(" ", "") + "1234";
	}

	public static String generateFacebookProfileSmallImageUrl(String facebookId) {
		return facebookProfileImageUrl.replaceAll("FACEBOOKID", facebookId);
	}

}
