package soulfoam.arenashared.main.account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValues {

	public static final int MAX_USERNAME_LENGTH = 20;
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean isValidEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
	}
		
}
