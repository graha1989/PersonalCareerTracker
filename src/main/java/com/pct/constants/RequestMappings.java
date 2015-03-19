package com.pct.constants;

/**
 * Constants for Controller mappings.
 * 
 * @author a.grahovac
 * 
 */
public final class RequestMappings {

	public static final String ROOT = "/";
	public static final String LOGIN = "/login";
	public static final String PASSWORD_CONTROLLER = "/password";

	/** URLs for Rest Controllers */
	public static final String SEARCH_API = "/api/search";
	public static final String REGISTER_API = "/api/register";
	public static final String DETAILS_API = "/api/details";

	public static final String SEARCH_API_ADMIN = "/api/search";
	public static final String DETAILS_API_ADMIN = "/api/details";
	public static final String EXPORT_ADMIN = "/api/export";

	public static final String LOAD_LIVING_EXPERIENCE_COUNTRIES = "lecountries";
	public static final String LOAD_LANGUAGES = "languages";
	public static final String LOAD_COUNTRIES = "countries";
	public static final String LOAD_SPECIALIZATIONS = "specializations";
	public static final String SEARCH = "s";

	public static final String ID = "id";

	public static final String QUERY = "q";
	public static final String USERNAME = "username";
	public static final String EMAIL = "email";
	public static final String CURRENT_PAGE = "currentPage";
	public static final String PASSWORD = "password";
	public static final String TOKEN = "token";

	public static final String LOGIN_PAGE = "login";
	public static final String PASSWORD_RESET_PAGE = "resetPassword";
	
	/**
	 * Constructor. Prevents initialization.
	 */
	private RequestMappings() {
		// EMPTY CONSTRUCTOR.
	}

}
