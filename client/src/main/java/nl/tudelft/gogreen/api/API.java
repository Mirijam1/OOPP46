package nl.tudelft.gogreen.api;

import com.mashape.unirest.http.HttpMethod;
import nl.tudelft.gogreen.api.servermodels.AchievedBadge;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.cache.Request;
import nl.tudelft.gogreen.shared.models.Activity;
import nl.tudelft.gogreen.shared.models.Category;
import nl.tudelft.gogreen.shared.models.CompletedAchievements;
import nl.tudelft.gogreen.shared.models.CompletedActivity;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;
import nl.tudelft.gogreen.shared.models.User;
import nl.tudelft.gogreen.shared.models.UserServer;
import nl.tudelft.gogreen.shared.models.social.Friendship;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class API {
    private static String locationUrl = "";

    /**
     * Prepares the API for use by this client application.
     *
     * @param local Boolean indicating whether this application is running in local mode, or not.
     */
    public static void prepareAPI(boolean local) {
        ServerConnection.initModelBuilder();

        locationUrl = local ? "http://localhost:8088/" : "https://oopp.timanema.net/";
    }

    /**
     * Closes the connection to the API, and terminates all running requests.
     */
    public static void closeAPI() {
        try {
            ServerConnection.closeServerConnection();
        } catch (IOException e) {
            throw new RuntimeException("Could not close connection to API gracefully");
        }
    }

    public static String buildUrl(String endPoint) {
        return locationUrl + endPoint;
    }

    public static String buildUrl(String endPoint, Object variable) {
        return locationUrl + endPoint.replace("{var}", variable + "");
    }

    /**
     * <p>Makes a request to the status endpoint. Can be used to check the connection,
     * or authentication.</p>
     * <p>Using the endpoint parameter, this method can theoretically be used to make a GET
     * request to any endpoint. However, it should be used only for status requests.</p>
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     * @param endpoint A {@link String} representing the endpoint to request from.
     */
    public static void requestStatus(ServerCallback<Object, BasicResponse> callback, String endpoint) {
        Request<Object> request = ServerConnection.buildSimpleRequest(HttpMethod.GET, buildUrl(endpoint));

        ServerConnection.request(BasicResponse.class, request, callback, true, 15);
    }

    /**
     * <p>Makes a fake request to the status endpoint. This function is more or less made to show the
     * functionality of mocking requests.</p>
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */
    public static void requestFakeStatus(ServerCallback<Object, BasicResponse> callback) {
        Request<Object> request = ServerConnection.buildSimpleRequest(HttpMethod.GET, "/api/fake/url");

        ServerConnection.mockRequest(BasicResponse.class,
                request,
                callback,
                true,
                15,
                new BasicResponse("Fake response from server", null),
                200);
    }

    /**
     * <p>Attempt to authenticate with the given {@link User}.</p>
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     * @param user     A {@link User} which represents the user credentials.
     */
    public static void attemptAuthentication(ServerCallback<Object, BasicResponse> callback, User user,
                                             String tfaToken) {
        String url = buildUrl(EndPoints.LOGIN);
        Map<String, Object> credentials = new HashMap<>();

        credentials.put("username", user.getUsername());
        credentials.put("password", user.getPassword());
        credentials.put("code", tfaToken);

        Request<Object> request = ServerConnection.buildRequestWithFields(HttpMethod.POST, url, credentials);

        ServerConnection.request(BasicResponse.class, request, callback, false, 0);
    }

    /**
     * <p>Submits the given activity.</p>
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     * @param activity A {@link SubmittedActivity} which represents the activity to submit
     */
    public static void submitActivity(ServerCallback<SubmittedActivity, SubmitResponse> callback,
                                      SubmittedActivity activity) {
        Request<SubmittedActivity> request = ServerConnection
                .buildRequestWithBody(HttpMethod.PUT, buildUrl(EndPoints.SUBMIT_ACTIVITY), activity);

        ServerConnection.request(SubmitResponse.class, request, callback, false, -1);
    }

    /**
     * <p>Submits a verification token to the server.</p>
     *
     * @param callback {@link ServerCallback} which will be called when the request returns
     * @param user     The {@link User} to verify
     * @param token    An {@link Integer} representing the token
     */
    public static void submitVerificationCode(ServerCallback<Object, BasicResponse> callback,
                                              User user, Integer token) {
        Request<Object> request = ServerConnection.buildSimpleRequest(HttpMethod.POST,
                buildUrl(EndPoints.VERIFY_USER, user.getExternalId()) + "?token=" + token);

        ServerConnection.request(BasicResponse.class, request, callback, false, -1);
    }

    /**
     * <p>Retrieves an array of {@link UserServer} with names matching the given name.</p>
     *
     * @param callback {@link ServerCallback} which will be called when the request returns
     * @param username A {@link String} representing the username to search fore
     */
    public static void searchForUsers(ServerCallback<Object, UserServer[]> callback, String username) {
        Request<Object> request = ServerConnection.buildSimpleRequest(HttpMethod.GET,
                buildUrl(EndPoints.SEARCH_FOR_USER, username));

        ServerConnection.request(UserServer[].class, request, callback, true, 15);
    }

    /**
     * <p>Sends a request to toggle two factor authentication.</p>
     *
     * @param callback {@link ServerCallback} which will be called when the request returns
     * @param toggle   A {@link Boolean} indicating whether 2FA should be toggled on or off
     */
    public static void toggleTwoFactorAuthentication(ServerCallback<Object, BasicResponse> callback, Boolean toggle) {
        Request<Object> request = ServerConnection
                .buildSimpleRequest(HttpMethod.POST, buildUrl(EndPoints.TOGGLE_2FA, toggle));

        ServerConnection.request(BasicResponse.class, request, callback, false, -1);
    }

    /**
     * <p>Attempts to confirm 2FA activation with the given token.</p>
     *
     * @param callback {@link ServerCallback} which will be called when the request returns
     * @param token    The token used for activation
     */
    public static void confirmTwoFactorAuthentication(ServerCallback<Object, BasicResponse> callback, String token) {
        Request<Object> request = ServerConnection
                .buildSimpleRequest(HttpMethod.POST, buildUrl(EndPoints.CONFIRM_2FA, token));

        ServerConnection.request(BasicResponse.class, request, callback, false, -1);
    }

    /**
     * retrieves Category List.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */

    public static void retrieveCategoryList(ServerCallback<Object, Category[]> callback) {
        String url = buildUrl(EndPoints.ALL_CATEGORIES);

        // Replace with real logger later
        System.out.println("Endpoint url:" + url);

        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(Category[].class, body, callback, true, -1);
    }

    /**
     * retrieve User's Completed Activities.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */

    public static void retrieveCompletedActivities(ServerCallback<Object, CompletedActivity[]> callback) {
        String url = buildUrl(EndPoints.GET_USER_ACTIVITIES);

        // Replace with real logger later
        System.out.println("Endpoint url:" + url);

        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);

        ServerConnection.request(CompletedActivity[].class, body, callback, false, 0);
    }

    /**
     * retrieves Activity List based on Category.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     * @param category name of the category to retrieve its relevant activities
     */
    public static void retrieveActivityList(ServerCallback<Object, Activity[]> callback, String category) {
        String url = buildUrl(EndPoints.FIND_ACTIVITIES_FROM_CATEGORY, category);

        // Replace with real logger later
        System.out.println("Endpoint url:" + url);

        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(Activity[].class, body, callback, true, -1);
    }

    /**
     * retrieve Fake User - User(TestUser).
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */
    public static void retrieveFakeUser(ServerCallback<Object, User> callback) {
        Request<Object> request = ServerConnection.buildSimpleRequest(HttpMethod.GET, "/api/user");

        ServerConnection.mockRequest(User.class,
                request,
                callback,
                false,
                -1,
                new User("TestUser", "123", false, "mail@example.com", 130f, null),
                200);
    }

    /**
     * retrieve FakeCO2 - testing method used to mock a to-be implemented API request.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */
    public static void retrieveFakeCo2(ServerCallback<Object, BasicResponse> callback) {
        Request<Object> request = ServerConnection.buildSimpleRequest(HttpMethod.GET, "/api/profile");

        ServerConnection.mockRequest(BasicResponse.class,
                request,
                callback,
                false,
                -1,
                new BasicResponse("0.4", null), 200);
    }

    /**
     * retrieves activity by id.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     * @param id       Integer id of the activity
     */
    public static void retrieveActivityById(ServerCallback<Object, Activity> callback, Integer id) {
        Request<Object> request = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, buildUrl(EndPoints.FIND_ACTIVITY_BY_ID, id));

        ServerConnection.request(Activity.class, request, callback, true, -1);
    }

    /**
     * Create a User - PUT method.
     *
     * @param callback - {@link ServerCallback} which will be called when the request returns.
     * @param user     user of this type {User} is created.
     */
    public static void createUser(ServerCallback<User, User> callback, User user) {
        String url = buildUrl(EndPoints.CREATE_USER);
        Request<User> body = ServerConnection
                .buildRequestWithBody(HttpMethod.PUT, url, user);

        ServerConnection.request(User.class, body, callback, true, -1);
    }

    /**
     * update user with new user attributes.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     * @param user     user updated
     */

    public static void updateUser(ServerCallback<User, User> callback, User user) {
        String url = buildUrl(EndPoints.UPDATE_USER);

        Request<User> body = ServerConnection
                .buildRequestWithBody(HttpMethod.PATCH, url, user);

        ServerConnection.request(User.class, body, callback);
    }

    /**
     * retrieve username of logged in user.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */
    public static void retrieveUser(ServerCallback<Object, UserServer> callback) {
        String url = buildUrl(EndPoints.GET_USER_INFO);

        // Replace with real logger later
        System.out.println("Endpoint url:" + url);

        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);

        ServerConnection.request(UserServer.class, body, callback, true, -1);
    }

    /**
     * retrieve user profile of logged in user.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */
    public static void retrieveUserProfile(ServerCallback<Object, UserServer> callback) {
        String url = buildUrl(EndPoints.GET_PROFILE);

        // Replace with real logger later
        System.out.println("Endpoint url:" + url);

        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);

        ServerConnection.request(UserServer.class, body, callback, false, -1);
    }

    /**
     * retrieve achieved badges of logged in user.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */
    public static void retrieveAchievedBadges(ServerCallback<Object, AchievedBadge[]> callback) {
        String url = buildUrl(EndPoints.GET_BADGES);
        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);

        ServerConnection.request(AchievedBadge[].class, body, callback, true, 0);
    }

    /**
     * retrieve friends leaderboard of logged in user.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */

    public static void retrieveFriendsLeaderboard(ServerCallback<Object, UserServer[]> callback) {
        String url = buildUrl(EndPoints.GET_FRIEND_LEADERBOARD);
        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(UserServer[].class, body, callback, true, 0);
    }

    /**
     * retrieve global leaderboard.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */
    public static void retrieveGlobalLeaderboard(ServerCallback<Object, UserServer[]> callback) {
        String url = buildUrl(EndPoints.GET_GLOBAL_LEADERBOARD);
        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(UserServer[].class, body, callback, true, 0);
    }

    /**
     * retrieve friends of logged in user.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */
    public static void retrieveFriends(ServerCallback<Object, Friendship[]> callback) {
        String url = buildUrl(EndPoints.GET_FRIENDS);
        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(Friendship[].class, body, callback, true, 0);
    }

    /**
     * add Friend.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     * @param username username of friend to add
     */
    public static void addFriend(ServerCallback<Object, Friendship> callback, String username) {
        String url = buildUrl(EndPoints.ADD_FRIEND_BY_NAME, username);
        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.PUT, url);
        ServerConnection.request(Friendship.class, body, callback, true, 0);
    }

    /**
     * retrieve pending received friend requests of logged in user.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */

    public static void retrievePendingReceivedFriendRequests(ServerCallback<Object, Friendship[]> callback) {
        String url = buildUrl(EndPoints.GET_RECEIVED_FRIEND_INVITES);
        Request<Object> body = ServerConnection
            .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(Friendship[].class, body, callback, true, 0);
    }

    /**
     * search User profiles.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     * @param username String Username
     */
    public static void searchUserProfiles(ServerCallback<Object, UserServer> callback, String username) {
        String url = buildUrl(EndPoints.SEARCH_USER_PROFILE, username);
        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(UserServer.class, body, callback, true, 0);
    }

    /**
     * retrieve pending sent friend requests of logged in user.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */
    public static void retrievePendingSentFriendRequests(ServerCallback<Object, Friendship[]> callback) {
        String url = buildUrl(EndPoints.GET_PENDING_SENT_FRIEND_INVITES);
        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(Friendship[].class, body, callback, true, 0);
    }

    /**
     * retrieve friend activities of logged in user.
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */
    public static void retrieveFriendActivities(ServerCallback<Object, CompletedActivity[]> callback) {
        String url = buildUrl(EndPoints.GET_FRIEND_ACTIVITY);
        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(CompletedActivity[].class, body, callback, true, 0);
    }

    /**
     * retrieve user completed achievements.
     *
     * @param callback servercallback
     */

    public static void retrieveAchievements(ServerCallback<Object, CompletedAchievements[]> callback) {
        String url = buildUrl(EndPoints.GET_COMPLETED_ACHIEVEMENTS);
        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(CompletedAchievements[].class, body, callback, true, 0);
    }

    /**
     * retrieve progressing achievements.
     *
     * @param callback servercallback
     */
    public static void retrieveProgressingAchievements(ServerCallback<Object, CompletedAchievements[]> callback) {
        String url = buildUrl(EndPoints.GET_PROGRESSING_ACHIEVEMENTS);
        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(CompletedAchievements[].class, body, callback, true, 0);
    }


}

