package nl.tudelft.gogreen.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.NonNull;
import nl.tudelft.gogreen.cache.Request;
import nl.tudelft.gogreen.cache.RequestCache;

import java.io.IOException;
import java.util.Map;

public class ServerConnection {
    protected static void initModelBuilder() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper objectMapper =
                new com.fasterxml.jackson.databind.ObjectMapper();

            @Override
            public <T> T readValue(String value, Class<T> valueClass) {
                try {
                    return objectMapper.readValue(value, valueClass);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public String writeValue(Object value) {
                try {
                    return objectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                return null;
            }
        });
    }

    protected static void closeServerConnection() throws IOException {
        Unirest.shutdown();
    }

    /**
     * <p>Makes the request to the server, and runs the given {@link ServerCallback} when the request returns.</p>
     * <p>This method makes some assumptions about caching, for the sake of easy usage. By default request made using
     * this method will use the cache with a TTL of 5 minutes.</p>
     * @param clazz Class of the object to map to
     * @param request {@link Request} to make
     * @param callback {@link ServerCallback} which will be ran after the request returns
     * @param <T> Type of the object to map to
     */
    protected static <T> void request(@NonNull Class<? extends T> clazz,
                                      @NonNull Request<T> request,
                                      @NonNull ServerCallback<T> callback) {
        request(clazz, request, callback, true, 5 * 60 * 60);
    }

    /**
     * <p>Makes the request to the server, and runs the given {@link ServerCallback} when the request returns.</p>
     * @param clazz Class of the object to map to
     * @param request {@link Request} to make
     * @param callback {@link ServerCallback} which will be ran after the request returns
     * @param useCache Boolean indicating whether this request should use the cache
     * @param ttl Time to live of the cache in seconds
     * @param <T> Type of the object to map to
     */
    protected static <T> void request(@NonNull Class<? extends T> clazz,
                                      @NonNull Request<T> request,
                                      @NonNull ServerCallback<T> callback,
                                      @NonNull boolean useCache,
                                      @NonNull int ttl) {
        final RequestCache cache = RequestCache.getInstance();

        if (useCache) {
            HttpResponse<T> cachedResponse = cache.retrieveFromCache(request);

            if (cachedResponse != null) {
                callback.result(cachedResponse.getBody(), cachedResponse, true, request);
                callback.run();
                return;
            }
        }

        request.buildHttpRequest().asObjectAsync(clazz, new Callback<T>() {
            @Override
            public void completed(HttpResponse<T> httpResponse) {
                if (useCache) {
                    cache.updateCache(request, httpResponse, ttl);
                }

                if (!callback.getCancelled().get()) {
                    callback.result(httpResponse.getBody(), httpResponse, false, request);
                    callback.run();
                }
            }

            @Override
            public void failed(UnirestException exception) {
                if (!callback.getCancelled().get()) {
                    callback.fail(exception);
                    callback.run();
                }
            }

            @Override
            public void cancelled() {
                if (!callback.getCancelled().get()) {
                    callback.fail(new UnirestException("Request cancelled"));
                    callback.run();
                }
            }
        });
    }

    protected static <T> Request<T> buildSimpleRequest(@NonNull HttpMethod method,
                                                       @NonNull String url) {
        return new Request<>(method, url);
    }

    protected static <T> Request<T> buildRequestWithFields(@NonNull HttpMethod method,
                                                           @NonNull String url,
                                                           @NonNull Map<String, Object> fields) {
        return new Request<>(method, url, fields, null);
    }

    protected static <T> Request<T> buildRequestWithBody(@NonNull HttpMethod method,
                                                           @NonNull String url,
                                                           @NonNull T body) {
        return new Request<>(method, url, null, body);
    }
}
