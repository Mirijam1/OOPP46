package nl.tudelft.gogreen.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.NoArgsConstructor;
import nl.tudelft.gogreen.cache.Request;
import nl.tudelft.gogreen.cache.RequestCache;
import org.objenesis.ObjenesisStd;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

@NoArgsConstructor
public class ServerConnection {
    protected static void initModelBuilder(com.fasterxml.jackson.databind.ObjectMapper mapper) {
        mapper.registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());

        Unirest.setObjectMapper(new ObjectMapper() {


            @Override
            public <T> T readValue(String value, Class<T> valueClass) {
                try {
                    return mapper.readValue(value, valueClass);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            public String writeValue(Object value) {
                try {
                    return mapper.writeValueAsString(value);
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
     *
     * @param clazz    Class of the object to map to
     * @param request  {@link Request} to make
     * @param callback {@link ServerCallback} which will be ran after the request returns
     * @param <T>      Type of the object to map to
     * @param <I>      Type of the object to map to
     */

    protected static <T, I> void request(Class<I> clazz,
                                         Request<T> request,
                                         ServerCallback<T, I> callback) {
        request(clazz, request, callback, true, 5 * 60 * 60);
    }

    /**
     * <p>Makes the request to the server, and runs the given {@link ServerCallback} when the request returns.</p>
     *
     * @param clazz    Class of the object to map to
     * @param request  {@link Request} to make
     * @param callback {@link ServerCallback} which will be ran after the request returns
     * @param useCache Boolean indicating whether this request should use the cache
     * @param ttl      Time to live of the cache in seconds, where -1 means 'until the program closes'
     * @param <T>      Type of the object to send
     * @param <I>      Type of the object to map to
     */
    protected static <T, I> void request(Class<I> clazz,
                                         Request<T> request,
                                         ServerCallback<T, I> callback,
                                         boolean useCache,
                                         int ttl) {
        final RequestCache cache = RequestCache.getInstance();

        if (useCache) {
            HttpResponse<I> cachedResponse = cache.retrieveFromCache(request);

            if (cachedResponse != null) {
                callback.result(cachedResponse.getBody(), cachedResponse, true, request);
                callback.run();
                return;
            }
        }

        request.buildHttpRequest().asObjectAsync(clazz, new Callback<I>() {
            @Override
            public void completed(HttpResponse<I> httpResponse) {
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

    /**
     * <p>Builds a fake response from the server. This method can be used to implement client-side functionality for
     * functions that have not yet been implemented server-side.</p>
     *
     * <p>Warning: This code qualifies to be called 'hacky' and might be a bit slow since it uses reflection.
     * The function mocks a basic {@link HttpResponse}, so some values might be null. This shouldn't be an issue, since
     * null-checks should be in place, but keep it in mind. By default, the statusText is set to 'Ok' (which you
     * cannot change) and all other fields except body and statuscode are null.</p>
     *
     * @param clazz              Class of the object to map to
     * @param request            {@link Request} to make
     * @param callback           {@link ServerCallback} which will be ran after the request returns
     * @param useCache           Boolean indicating whether this request should use the cache
     * @param ttl                Time to live of the cache in seconds, where -1 means 'until the program closes'
     * @param <T>                Type of the object to map to
     * @param <I>                Type of the object to map to
     * @param response           Object that will be put into the
     *                           {@link HttpResponse}, as if it was returned from the server.
     * @param responseStatusCode Status code that will be put into the {@link HttpResponse},
     *                           as if it was returned from the server.
     *                           Keep in mind that this parameter will not affect the status text field.
     */
    protected static <T, I> void mockRequest(Class<I> clazz,
                                             Request<T> request,
                                             ServerCallback<T, I> callback,
                                             boolean useCache,
                                             int ttl,
                                             I response,
                                             int responseStatusCode) {
        System.out.println(Thread.currentThread() + " => Creating mock request for '"
                + clazz.getName() + "' with settings ["
                + "useCache=" + useCache
                + ", ttl=" + ttl
                + ", request=" + request
                + ", response=" + response
                + "]");

        HttpResponse<I> httpResponse = null;
        try {
            httpResponse = new ObjenesisStd().getInstantiatorOf(HttpResponse.class).newInstance();

            // Status to 200
            Field statusCodeField = httpResponse.getClass().getDeclaredField("statusCode");
            statusCodeField.setAccessible(true);
            statusCodeField.set(httpResponse, responseStatusCode);

            // Text to 'Ok'
            Field statusTextField = httpResponse.getClass().getDeclaredField("statusText");
            statusTextField.setAccessible(true);
            statusTextField.set(httpResponse, "Ok");

            // Body to given response
            Field bodyField = httpResponse.getClass().getDeclaredField("body");
            bodyField.setAccessible(true);
            bodyField.set(httpResponse, response);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        callback.result(response, httpResponse, useCache, request);
        callback.run();
    }

    protected static <T> Request<T> buildSimpleRequest(HttpMethod method,
                                                       String url) {
        return new Request<>(method, url);
    }

    protected static <T> Request<T> buildRequestWithFields(HttpMethod method,
                                                           String url,
                                                           Map<String, Object> fields) {
        return new Request<>(method, url, fields, null);
    }

    protected static <T> Request<T> buildRequestWithBody(HttpMethod method,
                                                         String url,
                                                         T body) {
        return new Request<>(method, url, null, body);
    }
}
