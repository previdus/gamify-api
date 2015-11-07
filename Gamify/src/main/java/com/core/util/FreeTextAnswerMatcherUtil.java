package com.core.util;

import java.util.regex.Pattern;

public class FreeTextAnswerMatcherUtil {
	
	public static void main(String[] args) {
		System.out.println("fdfe");
		System.out.println(isAnswerCorrect("0.50", "0.500"));
	}  
	
	public static boolean isAnswerCorrect(String correctAns, String enteredAnswer){
		if(enteredAnswer == null )
			return false;
		correctAns = correctAns.trim();
		enteredAnswer = enteredAnswer.trim();
		correctAns = correctAns.toLowerCase();
		enteredAnswer = enteredAnswer.toLowerCase();
		  if(sameToSame(correctAns, enteredAnswer))
			  return true;
		  String sanitizedCorrectAns = sanitizedSpecialCharacters(correctAns);
		  String sanitizedEnteredAns = sanitizedSpecialCharacters(enteredAnswer);
		  if(sameToSame(sanitizedCorrectAns,sanitizedEnteredAns))
			  return true;
		  if(sameToSame(String.valueOf(sanitizedCorrectAns), sanitizedAlphabetCharacters(sanitizedEnteredAns)))
			  return true;
		  float correctAnsFloat = 0;
		  float enteredAnsFloat = 0;
		  try{
			  correctAnsFloat = Float.parseFloat(sanitizedCorrectAns);
			  enteredAnsFloat = Float.parseFloat(sanitizedAlphabetCharacters(sanitizedEnteredAns));
			  return enteredAnsFloat == correctAnsFloat ? true:false;
			  
		  }catch(Exception ex){
			  return false;
		  }
		  
	  }
	  
	  private static boolean sameToSame(String correctAns, String enteredAnswer){
		  return correctAns.trim().equalsIgnoreCase(enteredAnswer.trim());
	  }
	  
	  private static String sanitizedSpecialCharacters(String ans){
		  System.out.println("A : " + ans);
		  ans = ans.replaceAll(Pattern.quote(","), "");
		  ans = ans.replaceAll(Pattern.quote("%"), "");
		  ans = ans.replaceAll(Pattern.quote("$"), "");
		  ans = ans.replaceAll(Pattern.quote("rupees"), "");
		  ans = ans.replaceAll(Pattern.quote("Rs"), "");
		  ans = ans.replaceAll(Pattern.quote("rs"), "");
		  ans = ans.replaceAll(Pattern.quote("percent"), "");
		  System.out.println("B : " + ans);
		  return ans;
	  }
	  
	  
	  
	  private static String sanitizedAlphabetCharacters(String ans){
		  return ans.replaceAll(Pattern.quote("[^a-zA-Z]"), "");
	  }
	  
	 
}
