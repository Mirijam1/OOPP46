package nl.tudelft.gogreen.coapi;

import nl.tudelft.gogreen.coapi.config.Endpoints;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class API {
    public static MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    /**
     * create httpheaders to accept json.
     * @return
     */

    public static HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * build url based on endpoint.
     * @param endpoint based on query.
     * @return
     */
    public static String buildURL(String endpoint) {
        String url = Endpoints.baseURL + endpoint;
        System.out.println(url);
        return url;
    }

    /**
     * build url based on endpoint.
     * @param endpoint based on query.
     * @return
     */
    public static String buildLocalURL(String endpoint) {
        String url = Endpoints.localURL + endpoint;
        System.out.println(url);
        return url;
    }
}
