package com.core.util;

import java.awt.RenderingHints;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.media.jai.JAI;
import javax.media.jai.OpImage;
import javax.media.jai.RenderedOp;

import com.sun.media.jai.codec.SeekableStream;

public class GenericUtil {

	private static String facebookProfileImageUrl = "http://graph.facebook.com/FACEBOOKID/picture?type=small";

	public static String generateTemporaryPasswordBasedOnUserName(
			String userName) {

		return userName.replaceAll(" ", "") + "1234";
	}

	public static String generateFacebookProfileSmallImageUrl(String facebookId) {
		return facebookProfileImageUrl.replaceAll("FACEBOOKID", facebookId);
	}
	
	  public static  byte[] compress(byte[] originalImage) throws IOException {
	       
	        ByteArrayOutputStream os = new ByteArrayOutputStream();

	        InputStream is = new ByteArrayInputStream(originalImage);
	        BufferedInputStream bis = new BufferedInputStream(is);
	                
	        BufferedOutputStream bos = new BufferedOutputStream(
	                os);

	        SeekableStream s = SeekableStream.wrapInputStream(bis, true);

	        RenderedOp image = JAI.create("stream", s);
	        ((OpImage) image.getRendering()).setTileCache(null);

	        RenderingHints qualityHints = new RenderingHints(
	                RenderingHints.KEY_RENDERING,
	                RenderingHints.VALUE_RENDER_QUALITY);

	        RenderedOp resizedImage = JAI.create("SubsampleAverage", image, 0.9,
	                0.9, qualityHints);

	        JAI.create("encode", resizedImage, bos, "JPEG", null);
	        return os.toByteArray();

	    }

}
