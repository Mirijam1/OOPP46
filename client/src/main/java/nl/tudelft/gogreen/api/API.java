package nl.tudelft.gogreen.api;

import com.mashape.unirest.http.HttpMethod;
import nl.tudelft.gogreen.api.servermodels.Activity;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.Category;
import nl.tudelft.gogreen.api.servermodels.User;
import nl.tudelft.gogreen.cache.Request;

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

    /**
     * <p>Makes a request to the status endpoint. Can be used to check the connection,
     * or authentication.</p>
     * <p>Using the endpoint parameter, this method can theoretically be used to make a GET
     * request to any endpoint. However, it should be used only for status requests.</p>
     *
     * @param callback {@link ServerCallback} which will be called when the request returns.
     * @param endpoint A {@link String} representing the endpoint to request from.
     */
    public static void requestStatus(ServerCallback<BasicResponse> callback, String endpoint) {
        Request<BasicResponse> request = ServerConnection.buildSimpleRequest(HttpMethod.GET, buildUrl(endpoint));

        ServerConnection.request(BasicResponse.class, request, callback, true, 15);
    }

    /**
     * <p>Makes a fake request to the status endpoint. This function is more or less made to show the
     * functionality of mocking requests.</p>
     * @param callback {@link ServerCallback} which will be called when the request returns.
     */
    public static void requestFakeStatus(ServerCallback<BasicResponse> callback) {
        Request<BasicResponse> request = ServerConnection.buildSimpleRequest(HttpMethod.GET, "/api/fake/url");

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
    public static void attemptAuthentication(ServerCallback<BasicResponse> callback, User user) {
        String url = buildUrl(EndPoints.LOGIN);
        Map<String, Object> credentials = new HashMap<>();

        credentials.put("username", user.getName());
        credentials.put("password", user.getPassword());

        Request<BasicResponse> request = ServerConnection.buildRequestWithFields(HttpMethod.POST, url, credentials);

        ServerConnection.request(BasicResponse.class, request, callback, false, 0);
    }

    /* Please add javadocs below */

    public static void retrieveCategoryList(ServerCallback<Category[]> callback) {
        String url = buildUrl(EndPoints.CATEGORYLIST);

        // Replace with real logger later
        System.out.println("Endpoint url:" + url);

        Request<Category[]> body = ServerConnection
            .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(Category[].class, body, callback, true, -1);
    }

    public static void retrieveActivityList(ServerCallback<Activity[]> callback, String category) {
        String url = buildUrl(EndPoints.ACTIVITYLIST) + category;

        // Replace with real logger later
        System.out.println("Endpoint url:" + url);

        Request<Activity[]> body = ServerConnection
            .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(Activity[].class, body, callback, true, -1);
    }

    public static void retrieveFakeCo2(ServerCallback<BasicResponse> callback) {
        Request<BasicResponse> request = ServerConnection.buildSimpleRequest(HttpMethod.GET, "/api/co2here");

        ServerConnection.mockRequest(BasicResponse.class,
            request,
            callback,
            true,
            15,
            new BasicResponse("co2 here"),
            200);
    }
}

