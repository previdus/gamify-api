package com.core.util;

public class RandomStringGenerator {

      /**
       * The random number generator.
       * 
       * @param MIN_LENGTH
       *              the length of generated random number
       * @return the random string
       */

      public static String getRandomString(final int MIN_LENGTH) {
            /*
             * Set of characters that is valid. Must be printable, memorable,
             * and "won't break HTML" (i.e., not ' <', '>', '&', '=', ...). or
             * break shell commands (i.e., not ' <', '>', '$', '!', ...). I, L
             * and O are good to leave out, as are numeric zero and one.
             */
            final char[] goodChar = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's',
                        't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N',
                        'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9',
                        '1', '@', '#' };

            final java.util.Random r = new java.util.Random();
            final StringBuffer sb = new StringBuffer();
            for (int i = 0; i < MIN_LENGTH; i++) {
                  sb.append(goodChar[r.nextInt(goodChar.length)]);
            }
            return sb.toString();
      }

}
