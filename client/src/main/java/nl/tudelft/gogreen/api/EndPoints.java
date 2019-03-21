package nl.tudelft.gogreen.api;

public class EndPoints {
    // STATUS ENDPOINTS
    public static final String STATUS = "api/status/test";
    public static final String STATUS_USER = "api/status/restricted/test";
    public static final String STATUS_ADMIN = "api/status/admin/test";

    // USER ENDPOINTS
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";

    //HttpMethod put
    public static final String CREATEUSER = "api/user/create";
    //PATCH  - user,authentication token
    public static final String UPDATEUSER = "api/user/update";

    // PROFILE ENDPOINTS
    public static final String GETPOINTS = "api/profile/";

    public static final String GETUSERACTIVITIES = "api/profile/activities/";
    public static final String GETSPECIFICACTIVITY = "api/profile/activities/{externalID}";

    public static final String GETBADGES = "api/profile/badges";


    // ACTIVITY ENDPOINTS
    public static final String ALL_CATEGORIES = "api/categories/";
    public static final String FIND_CATEGORY = "api/categories/{var}";
    public static final String SUBMIT_ACTIVITY = "api/profile/activities/submit";

    public static final String ACTIVITY = "api/activities/";
    public static final String FIND_ACTIVITIES_FROM_CATEGORY = "api/activities/category/{var}";

    //
    public static final String CREATE = "api/user/create";
    public static final String UPDATE = "api/user/update";
    public static final String DELETE = "api/user/delete";


}
