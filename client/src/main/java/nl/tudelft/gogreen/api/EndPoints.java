package nl.tudelft.gogreen.api;

public class EndPoints {
    // STATUS ENDPOINTS
    public static final String STATUS = "api/status/test";
    public static final String STATUS_USER = "api/status/restricted/test";
    public static final String STATUS_ADMIN = "api/status/admin/test";

    // USER ENDPOINTS
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";

    // PROFILE ENDPOINTS

    // ACTIVITY ENDPOINTS
    public static final String ALL_CATEGORIES = "api/categories/";
    public static final String FIND_CATEGORY = "api/categories/{var}";
    public static final String SUBMIT_ACTIVITY = "api/profile/activities/submit";

    public static final String ACTIVITY = "api/activities/";
    public static final String FIND_ACTIVITIES_FROM_CATEGORY = "api/activities/category/{var}";

}
