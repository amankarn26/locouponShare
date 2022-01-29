package com.locoupon.common.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.locoupon.common.enums.RequestTypes;
import com.locoupon.common.enums.RequestTypes.ErrorCodes;
public class AppStringUtils {

	private static Integer MAX_STR_LENGTH = 256;
	private static Integer MAX_LONG_STR_LENGTH = 2056;
	private static Integer VALID_LEGAL_AGE = 18;

	private static final Logger logger = LoggerFactory.getLogger(AppStringUtils.class);

	public static RequestTypes.ErrorCodes validateStr(String str, ErrorCodes reqError) {
		// RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE ;
		if (str.equals("") || null == str || str.length() > MAX_STR_LENGTH) {
			reqError = reqError.INVALID_STR;
			return reqError;
		}
		return reqError;
	}

	public static ErrorCodes validateLongStr(String str, ErrorCodes reqError) {
		// RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE ;
		if (str.length() > MAX_LONG_STR_LENGTH) {
			reqError = reqError.INVALID_STR;
			return reqError;
		}
		return reqError;
	}

	public static ErrorCodes validateNameStr(String str, ErrorCodes reqError) {
		// RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE ;
		if (str.length() > MAX_STR_LENGTH) {
			reqError = reqError.INVALID_STR;
			return reqError;
		}
		boolean valName = validateLetters(str);
		if (!valName) {
			reqError = reqError.INVALID_CHARNAME;
			return reqError;
		} else {
			return reqError;
		}
	}

	public static boolean validateLetters(String txt) {

		String regx = "[a-zA-Z]+\\.?";
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(txt);
		return matcher.find();

	}

	public static ErrorCodes validateNums(String str, ErrorCodes reqError) {
		// RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE ;
		boolean valNos = validateNumbers(str);
		if (!valNos) {
			reqError = reqError.INVALID_NUMBER;
			return reqError;
		} else {
			return reqError;
		}

	}

	public static boolean validateNumbers(String nos) {
		// String number = "012-1234567";
		Pattern pattern = Pattern.compile("\\d{8,12}");
		// Pattern pattern1 = Pattern.compile("\\d{4}[-\\.\\s]\\d{7}");
		Matcher matcher = pattern.matcher(nos);
		// Matcher matcher1 = pattern1.matcher(nos);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static ErrorCodes validateAge(Date doB, ErrorCodes reqError) {
		// RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE ;
		LocalDate ld = new java.sql.Date(doB.getTime()).toLocalDate();
		int age = calculateAge(ld, LocalDate.now());
		if (age < VALID_LEGAL_AGE) {
			reqError = reqError.INVALID_LEGAL_AGE;
			return reqError;
		}
		return reqError;

	}

	public static int getAge(Date doB) {
		// RequestTypes.ErrorCodes reqError = RequestTypes.ErrorCodes.NONE ;
		LocalDate ld = new java.sql.Date(doB.getTime()).toLocalDate();
		int age = calculateAge(ld, LocalDate.now());
		return age;

	}

	public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
		if ((birthDate != null) && (currentDate != null)) {
			return Period.between(birthDate, currentDate).getYears();
		} else {
			return 0;
		}
	}

	/*
	 * public static ErrorCodes validateGuardianType(GuardianType getgType,
	 * ErrorCodes reqError) { switch (getgType) { case FATHER: reqError =
	 * reqError.NONE; return reqError;
	 * 
	 * case MOTHER: reqError = reqError.NONE; return reqError;
	 * 
	 * case HUSBAND: reqError = reqError.NONE; return reqError;
	 * 
	 * case OTHER: reqError = reqError.NONE; return reqError;
	 * 
	 * case NONE: reqError = reqError.NONE; return reqError;
	 * 
	 * default: reqError = reqError.INVALID_GUARDIAN_TYPE; return reqError;
	 * 
	 * } }
	 */

	public static boolean validatePin(String pincode, ErrorCodes reqError) {
		Pattern pattern = Pattern.compile("\\d{6}");
		Matcher matcher = pattern.matcher(pincode);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	// TODO - valdiate both linux and win path
	public static ErrorCodes validatePathStr(String docpath, ErrorCodes reqError) {
		// TODO Auto-generated method stub
		return reqError.NONE;
	}

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}

	public static ErrorCodes validateEmailID(String emailid, ErrorCodes reqError) {

		boolean match = validate(emailid);
		if (match) {
			return reqError.NONE;
		} else {
			return reqError.INVALID_EMAIL_ID;
		}

	}

	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";

	/**
	 * Validate password with regular expression
	 * 
	 * @param password password for validation
	 * @return true valid password, false invalid password
	 */
	public static boolean validatePass(final String password) {

		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();

	}

	public static ErrorCodes validatePasswd(String password, ErrorCodes reqError) {

		boolean match = validatePass(password);
		if (match) {
			return reqError.NONE;
		} else {
			return reqError.INVALID_PASSWORD;
		}
	}
}
