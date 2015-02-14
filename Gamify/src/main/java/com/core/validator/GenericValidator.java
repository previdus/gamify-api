package com.core.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class GenericValidator {

	private Pattern emailPattern, userNamePattern, passwordPattern,
			phoneNumPattern;
	private Matcher matcher;

	/*
	 * ^ #start of the line [_A-Za-z0-9-\\+]+ # must start with string in the
	 * bracket [ ], must contains one or more (+) ( # start of group #1
	 * \\.[_A-Za-z0-9-]+ # follow by a dot "." and string in the bracket [ ],
	 * must contains one or more (+) )* # end of group #1, this group is
	 * optional (*)
	 * 
	 * @ # must contains a "@" symbol [A-Za-z0-9-]+ # follow by string in the
	 * bracket [ ], must contains one or more (+) ( # start of group #2 - first
	 * level TLD checking \\.[A-Za-z0-9]+ # follow by a dot "." and string in
	 * the bracket [ ], must contains one or more (+) )* # end of group #2, this
	 * group is optional (*) ( # start of group #3 - second level TLD checking
	 * \\.[A-Za-z]{2,} # follow by a dot "." and string in the bracket [ ], with
	 * minimum length of 2 ) # end of group #3 $ #end of the line
	 */
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/*
	 * any character sequence containing at least 3 and up to 30
	 */
	private static final String USERNAME_PATTERN = "(.{3,30})";

	/*
	 * ( # Start of group (?=.*\d) # must contains one digit from 0-9
	 * (?=.*[a-z]) # must contains one lowercase characters (?=.*[A-Z]) # must
	 * contains one uppercase characters (?=.*[@#$%]) # must contains one
	 * special symbols in the list "@#$%" . # match anything with previous
	 * condition checking {6,20} # length at least 6 characters and maximum of
	 * 20 ) # End of group
	 * "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	 * 
	 * 
	 * However we are just doing simple pattern for now with 6 to 20 characters
	 */
	private static final String PASSWORD_PATTERN = "(.{6,20})";

	private static final String PHONENUM_PATTERN = "^(([(]?(\\d{2,4})[)]?)|(\\d{2,4})|([+1-9]+\\d{1,2}))?[-\\s]?(\\d{2,3})?[-\\s]?((\\d{7,8})|(\\d{3,4}[-\\s]\\d{3,4}))$";

	public GenericValidator() {
		emailPattern = Pattern.compile(EMAIL_PATTERN);
		userNamePattern = Pattern.compile(USERNAME_PATTERN);
		passwordPattern = Pattern.compile(PASSWORD_PATTERN);
		phoneNumPattern = Pattern.compile(PHONENUM_PATTERN);
	}

	/**
	 * Validate hex with regular expression
	 * 
	 * @param hex
	 *            hex for validation
	 * @return true valid hex, false invalid hex
	 */
	public boolean emailValidate(final String hex) {
		matcher = emailPattern.matcher(hex);
		return matcher.matches();
	}

	public boolean userNameValidate(final String userName) {
		matcher = userNamePattern.matcher(userName);
		return matcher.matches();
	}

	public boolean passwordValidate(final String password) {
		matcher = passwordPattern.matcher(password);
		return matcher.matches();
	}

	public boolean phoneNumValidate(final String phoneNum) {
		matcher = phoneNumPattern.matcher(phoneNum);
		return matcher.matches();
	}
}