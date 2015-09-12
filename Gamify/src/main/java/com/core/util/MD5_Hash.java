package com.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author  - Used for computing MD5 HAsh 0f a given String
 */
public class MD5_Hash {

      /**
       * @param: Byte Array
       * @return: Hex String
       */
      private static String convertToHex(final byte[] data) {
            final StringBuffer buf = new StringBuffer();
            for (int i = 0; i < data.length; i++) {
                  int halfbyte = (data[i] >>> 4) & 0x0F;
                  int two_halfs = 0;
                  do {
                        if ((0 <= halfbyte) && (halfbyte <= 9)) {
                              buf.append((char) ('0' + halfbyte));
                        } else {
                              buf.append((char) ('a' + (halfbyte - 10)));
                        }
                        halfbyte = data[i] & 0x0F;
                  } while (two_halfs++ < 1);
            }
            return buf.toString();
      }

      /**
       * @param text
       *              - String whose MD5 hash is to be computed
       * @return Byte Array converted to a Hex String using the local Function
       *         'convertToHex'
       */
      public static String MD5(final String text) {
            try {
                  final MessageDigest md = MessageDigest.getInstance("MD5");

                  md.update(text.getBytes("iso-8859-1"), 0, text.length());
                  final byte[] md5hash = md.digest();

                  return convertToHex(md5hash);
            } catch (final NoSuchAlgorithmException e) {
                  e.printStackTrace();
                  return null;
            } catch (final UnsupportedEncodingException e) {
                  e.printStackTrace();
                  return null;
            }
      }

}
