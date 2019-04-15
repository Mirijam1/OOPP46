package nl.tudelft.gogreen.coapi;

import nl.tudelft.gogreen.coapi.config.Endpoints;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class API {

    /**
     * create httpheaders to accept json.
     *
     * @return HttpHeaders
     */

    public static HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * build url based on endpoint.
     *
     * @param endpoint based on query.
     * @return String
     */
    public static String buildURL(String endpoint) {
        String url = Endpoints.baseURL + endpoint;
        System.out.println(url);
        return url;
    }

}
