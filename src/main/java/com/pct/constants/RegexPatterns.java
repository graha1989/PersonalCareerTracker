package com.pct.constants;

/**
 * Constants for Regex patterns.
 * 
 * @author a.grahovac
 * 
 */
public class RegexPatterns {
	public static final String LETTERS_ONLY = "^[a-zA-ZčČćĆšŠđĐžŽ ]*$";
	public static final String NUMBERS_ONLY = "^[0-9 ]*$";
	public static final String ZIP_CODE = "^[1-9][0-9]{3} ?[a-zA-Z]{2}$";
	public static final String PHONE_NUMBER = "(^\\+[0-9]{2}|^\\+[0-9]{2}\\(0\\)|^\\(\\+[0-9]{2}\\)\\(0\\)|^00[0-9]{2}|^0)([0-9]{9}$|[0-9\\-\\s]{10}$)";

	/**
	 * Constructor. Prevents initialization.
	 */
	private RegexPatterns() {
		// EMPTY CONSTRUCTOR.
	}

}
