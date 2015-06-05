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

	/**
	 * Constructor. Prevents initialization.
	 */
	private RegexPatterns() {
		// EMPTY CONSTRUCTOR.
	}

}
