package nl.tudelft.gogreen.api;

public class EndPoints {
    // STATUS ENDPOINTS
    public static final String STATUS = "api/status/test"; // GET
    public static final String STATUS_USER = "api/status/restricted/test"; // GET
    public static final String STATUS_ADMIN = "api/status/admin/test"; // GET

    // USER ENDPOINTS
    public static final String LOGIN = "login"; // POST
    public static final String LOGOUT = "logout"; // GET
    public static final String GET_USER_INFO = "api/user/"; // GET
    public static final String CREATE_USER = "api/user/create"; // PUT
    public static final String UPDATE_USER = "api/user/update"; // DELETE

    // PROFILE ENDPOINTS
    public static final String GET_PROFILE = "api/profile/"; // GET
    public static final String SUBMIT_ACTIVITY = "api/profile/activities/submit"; // PUT
    public static final String GET_USER_ACTIVITIES = "api/profile/activities/"; // GET
    public static final String GET_SPECIFIC_ACTIVITY = "api/profile/activities/{var}"; // GET
    public static final String GET_BADGES = "api/profile/badges"; // GET
    public static final String GET_COMPLETED_ACHIEVEMENTS = "api/profile/achievements"; // GET
    public static final String GET_PROGRESSING_ACHIEVEMENTS = "api/profile/achievements/progressing"; // GET
    public static final String GET_FRIENDS = "api/profile/friends"; // GET
    public static final String GET_PENDING_SENT_FRIEND_INVITES = "api/profile/friends/pending"; // GET
    public static final String GET_RECEIVED_RIEND_INVITES = "api/profile/friends/invites"; // GET

    // SOCIAL ENDPOINTS
    public static final String ADD_FRIEND_BY_NAME = "api/social/friends/add/{var}"; // PUT
    public static final String DELETE_FRIEND_BY_NAME = "api/social/friends/delete/{var}"; // DELETE
    public static final String GET_FRIEND_ACTIVITY = "api/social/friends/activities"; // GET

    // LEADERBOARD ENDPOINTS
    public static final String GET_GLOBAL_LEADERBOARD = "api/leaderboard/global"; // GET
    public static final String GET_FRIEND_LEADERBOARD = "api/leaderboard/friends"; // GET

    // CATEGORY ENDPOINTS
    public static final String ALL_CATEGORIES = "api/categories/"; // GET
    public static final String FIND_CATEGORY = "api/categories/{var}"; // GET

    // ACTIVITY ENDPOINTS
    public static final String FIND_ACTIVITY_BY_ID = "api/activities/{var}"; // GET
    public static final String FIND_ACTIVITY_OPTIONS_BY_ID = "api/activities/{var}/options"; // GET
    public static final String FIND_ACTIVITIES_FROM_CATEGORY = "api/activities/category/{var}"; // GET
}
