package nl.tudelft.gogreen.api;

import com.mashape.unirest.http.HttpMethod;
import nl.tudelft.gogreen.api.servermodels.Activity;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.Category;
import nl.tudelft.gogreen.api.servermodels.CompletedActivity;
import nl.tudelft.gogreen.api.servermodels.User;
import nl.tudelft.gogreen.cache.Request;
import nl.tudelft.gogreen.shared.models.SubmitResponse;
import nl.tudelft.gogreen.shared.models.SubmittedActivity;

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

        // TODO: Read these links from property file
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

    private static String buildUrl(String endPoint) {
        return locationUrl + endPoint;
    }

    private static String buildUrl(String endPoint, String variable) {
        return locationUrl + endPoint.replace("{var}", variable);
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
            new BasicResponse("Fake response from server"),
            200);
    }

    /**
     * <p>Attempt to authenticate with the given {@link User}.</p>
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     * @param user     A {@link User} which represents the user credentials.
     */
    public static void attemptAuthentication(ServerCallback<Object, BasicResponse> callback, User user) {
        String url = buildUrl(EndPoints.LOGIN);
        Map<String, Object> credentials = new HashMap<>();

        credentials.put("username", user.getUsername());
        credentials.put("password", user.getPassword());

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

    /* Please add javadocs below */

    public static void retrieveCategoryList(ServerCallback<Object, Category[]> callback) {
        String url = buildUrl(EndPoints.ALL_CATEGORIES);

        // Replace with real logger later
        System.out.println("Endpoint url:" + url);

        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(Category[].class, body, callback, true, -1);
    }

    public static void retrieveCompletedActivities(ServerCallback<Object, CompletedActivity[]> callback) {
        String url = buildUrl("api/profile/activities");

        // Replace with real logger later
        System.out.println("Endpoint url:" + url);

        Request<Object> body = ServerConnection
                .buildSimpleRequest(HttpMethod.GET, url);

        ServerConnection.request(CompletedActivity[].class, body, callback, true, 15);
    }

    public static void retrieveActivityList(ServerCallback<Object, Activity[]> callback, String category) {
        String url = buildUrl(EndPoints.FIND_ACTIVITIES_FROM_CATEGORY, category);

        // Replace with real logger later
        System.out.println("Endpoint url:" + url);

        Request<Object> body = ServerConnection
            .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(Activity[].class, body, callback, true, -1);
    }

    public static void retrieveFakeUser(ServerCallback<Object, User> callback) {
        Request<Object> request = ServerConnection.buildSimpleRequest(HttpMethod.GET, "/api/user");

        ServerConnection.mockRequest(User.class,
                request,
                callback,
                false,
                -1,
                new User("TestUser", "123", 130f),
                200);
    }

    public static void createUser(ServerCallback<User, User> callback, User user) {
        //Check if this is correct
        String url = buildUrl(EndPoints.CREATE);

        Request<User> body = ServerConnection
                .buildRequestWithBody(HttpMethod.PUT, url, user);
        System.out.println(user);
        ServerConnection.request(User.class, body, callback);
    }

    public static void updateUser(ServerCallback<User, User> callback, User user) {
        String url = buildUrl(EndPoints.UPDATE);

        Request<User> body = ServerConnection
                .buildRequestWithBody(HttpMethod.PUT, url, user);

        ServerConnection.request(User.class, body, callback);
    }

//    public static void deleteUser(ServerCallback<Object, Map<String, String>> callback) {
//        String url = buildUrl(EndPoints.DELETE);
//
//        Request<Object> body = ServerConnection.buildSimpleRequest(HttpMethod.DELETE, url);
//
//        ServerConnection.request(Map.class, body, callback);
//    }
}

