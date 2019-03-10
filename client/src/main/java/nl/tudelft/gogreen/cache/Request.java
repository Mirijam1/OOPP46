package nl.tudelft.gogreen.cache;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Request<T> {
    private @NonNull HttpMethod method;
    private @NonNull String url;
    private Map<String, Object> fields;
    private T data;

    public void clearCache() {
        RequestCache.getInstance().clearCache(this);
    }

    /**
     * <p>Returns a {@link BaseRequest} based on the parameters stored in the request.
     * The {@link BaseRequest} can be used to make the actual request.</p>
     * @return A {@link BaseRequest} initialized with the parameters of the request
     */
    public BaseRequest buildHttpRequest() {
        HttpRequestWithBody request = new HttpRequestWithBody(method, url)
            .header("accept", "application/json");

        if (data != null) {
            return request.header("Content-Type", "application/json").body(data);
        }

        if (fields != null) {
            request.fields(fields);
            return request;
        }

        return request;
    }
}
