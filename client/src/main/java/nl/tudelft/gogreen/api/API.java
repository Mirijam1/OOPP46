package nl.tudelft.gogreen.api;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.request.HttpRequestWithBody;
import nl.tudelft.gogreen.api.servermodels.Activity;
import nl.tudelft.gogreen.api.servermodels.BasicResponse;
import nl.tudelft.gogreen.api.servermodels.Category;
import nl.tudelft.gogreen.api.servermodels.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        HttpRequestWithBody body = ServerConnection.buildSimpleRequest(HttpMethod.GET,
            buildUrl(endpoint));

        ServerConnection.request(BasicResponse.class, body, callback);
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

        HttpRequestWithBody body = ServerConnection
            .buildRequestWithFields(url, HttpMethod.POST, credentials);

        ServerConnection.request(BasicResponse.class, body, callback);
    }

    public static void retrieveCategoryList(ServerCallback<Category[]> callback) {
        String url = buildUrl(EndPoints.CATEGORY);

        // Replace with real logger later
        System.out.println("Endpoint url:" + url);

        HttpRequestWithBody body = ServerConnection
            .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(Category[].class, body, callback);
    }
    public static void retrieveActivityList(ServerCallback<Activity[]> callback) {
        String url = buildUrl(EndPoints.ACTIVITY);

        // Replace with real logger later
        System.out.println("Endpoint url:" + url);

        HttpRequestWithBody body = ServerConnection
            .buildSimpleRequest(HttpMethod.GET, url);
        ServerConnection.request(Activity[].class, body, callback);
    }
}

