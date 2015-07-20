package com.core.constants;

import org.apache.commons.lang.StringEscapeUtils;

public class CDNConstants {
	
	private static final String API_KEY = "465561835822868";
	private static final String UPLOAD_PRESET = "ppp5arjd";
	private static final String PUBLIC_ID="previdus";
	
	
	
	public static String getRequiredEscapedHtmlJsonStringForCloudinaryImageUpload(){
		return StringEscapeUtils.escapeHtml("{" +
				 "\"timestamp\": "+System.currentTimeMillis()+"," +
				 "\"api_key\": \""+API_KEY+"\", " +
				 "\"upload_preset\": \""+UPLOAD_PRESET+"\","+
				"\"public_id\": \""+PUBLIC_ID+"\""+
				"}");
	}
	
	

}
