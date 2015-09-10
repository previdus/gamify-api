package com.core.util;

import org.springframework.security.crypto.codec.Base64;

public class StringEncryptionDecryptionUtil {
	
	 public static String encrypt(String valueToEnc) throws Exception {
	 
		byte[] encValue = Base64.encode(valueToEnc.getBytes());	    
	    return new String(encValue);
	    
	}

	public static String decrypt(String valueToDecode) throws Exception {
        byte[] decodedVallue = Base64.decode(valueToDecode.getBytes());	    
	    return new String(decodedVallue);
	    
	}

	
}
