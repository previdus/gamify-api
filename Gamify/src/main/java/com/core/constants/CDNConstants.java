package com.core.constants;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.apache.commons.lang.StringEscapeUtils;

public class CDNConstants {
	
	private static final String API_KEY = "465561835822868";
	private static final String UPLOAD_PRESET = "ppp5arjd";
	
	public static final String CLOUDINARY_RELATIVE_URL="http://res.cloudinary.com/previdus/";
	public static final String IMAGE_UPLOAD_SEPARATOR = "#";
	private static SecureRandom random = new SecureRandom();
	
	
	public static String getRequiredEscapedHtmlJsonStringForCloudinaryImageUpload(){
		return StringEscapeUtils.escapeHtml("{" +
				 "\"timestamp\": "+System.currentTimeMillis()+"," +
				 "\"api_key\": \""+API_KEY+"\", " +
				 "\"upload_preset\": \""+UPLOAD_PRESET+"\","+
				"\"public_id\": \""+ (new BigInteger(130, random).toString(32))+"\""+
				"}");
	}
	
	

}
