package nl.tudelft.gogreen.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import lombok.NonNull;

import java.io.IOException;
import java.util.HashMap;
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

    protected static <T> void request(Class<T> objectMap,
                                    BaseRequest request,
                                    ServerCallback<T> serverCallback) {
        request.asObjectAsync(objectMap, new Callback<T>() {
            @Override
            public void completed(HttpResponse<T> httpResponse) {
                if (!serverCallback.getCancelled().get()) {
                    serverCallback.result(httpResponse.getBody(), httpResponse);
                    serverCallback.run();
                }
            }

            @Override
            public void failed(UnirestException e) {
                if (!serverCallback.getCancelled().get()) {
                    serverCallback.fail(e);
                    serverCallback.run();
                }
            }

            @Override
            public void cancelled() {
                if (!serverCallback.getCancelled().get()) {
                    serverCallback.fail(new UnirestException("Request cancelled"));
                    serverCallback.run();
                }
            }
        });
    }

    protected static <T> RequestBodyEntity buildRequestWithBody(HttpMethod method,
                                                              String url, @NonNull T body) {
        return new HttpRequestWithBody(method, url).body(body);
    }

    protected static HttpRequestWithBody buildSimpleRequest(HttpMethod method,
                                                            String url) {
        return buildRequestWithFields(url, method, new HashMap<>());
    }

    protected static HttpRequestWithBody buildRequestWithFields(String url,
                                                        HttpMethod method,
                                                        @NonNull Map<String, Object> fields) {
        HttpRequestWithBody request = new HttpRequestWithBody(method, url);

        request.fields(fields);

        return request;
    }
}
