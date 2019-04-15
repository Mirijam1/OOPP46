package nl.tudelft.gogreen.client.tests.api;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import nl.tudelft.gogreen.api.ServerCallback;
import nl.tudelft.gogreen.api.ServerConnection;
import nl.tudelft.gogreen.cache.Request;
import nl.tudelft.gogreen.cache.RequestCache;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RequestCache.class, com.fasterxml.jackson.databind.ObjectMapper.class})
public class ServerConnectionTest {
    private RequestCache requestCache;
    private TestConnection testConnection;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    class TestConnection extends ServerConnection {
        protected <T, I> void testRequest(Class<I> clazz,
                                          Request<T> request,
                                          ServerCallback<T, I> callback) {
            request(clazz, request, callback, true, 5 * 60 * 60);
        }

        protected <T, I> void testRequest(Class<I> clazz,
                                          Request<T> request,
                                          ServerCallback<T, I> callback,
                                          boolean useCache,
                                          int ttl) {
            request(clazz, request, callback, useCache, ttl);
        }

        protected <T, I> void testMockRequest(Class<I> clazz,
                                              Request<T> request,
                                              ServerCallback<T, I> callback,
                                              boolean useCache,
                                              int ttl,
                                              I response,
                                              int responseStatusCode) {
            mockRequest(clazz, request, callback, useCache, ttl, response, responseStatusCode);
        }

        protected <T> Request<T> testBuildSimpleRequest(HttpMethod method,
                                                        String url) {
            return buildSimpleRequest(method, url);
        }

        protected <T> Request<T> testBuildRequestWithFields(HttpMethod method,
                                                            String url,
                                                            Map<String, Object> fields) {
            return buildRequestWithFields(method, url, fields);
        }

        protected <T> Request<T> testBuildRequestWithBody(HttpMethod method,
                                                          String url,
                                                          T body) {
            return buildRequestWithBody(method, url, body);
        }
    }

    @Before
    public void setUp() {
        testConnection = new TestConnection();
        requestCache = Mockito.mock(RequestCache.class);
        PowerMockito.mockStatic(RequestCache.class);

        when(RequestCache.getInstance()).thenReturn(requestCache);
    }

    @Test
    public void shouldReturnFromCache() {
        Request<Object> request = new Request<>(HttpMethod.GET, "url");
        HttpResponse response = Mockito.mock(HttpResponse.class);

        when(response.getStatus()).thenReturn(42);
        when(response.getBody()).thenReturn("");
        when(requestCache.retrieveFromCache(request)).thenReturn(response);

        testConnection.testRequest(Object.class, request, new ServerCallback<Object, Object>() {
            @Override
            public void run() {
                assertEquals(42, getStatusCode());
            }
        });
    }

    @Test
    public void shouldCreateProperRequest1() {
        Request request = testConnection.testBuildSimpleRequest(HttpMethod.GET, "");

        assertEquals(request.getMethod(), HttpMethod.GET);
    }

    @Test
    public void shouldCreateProperRequest2() {
        Request<String> request = testConnection.testBuildRequestWithBody(HttpMethod.GET, "", "awd");

        assertEquals(request.getMethod(), HttpMethod.GET);
        assertEquals(request.getData(), "awd");
    }

    @Test
    public void shouldCreateProperRequest3() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("life", 42);
        Request request = testConnection.testBuildRequestWithFields(HttpMethod.GET, "", fields);

        assertEquals(request.getMethod(), HttpMethod.GET);
        assertEquals(request.getFields().get("life"), 42);
    }
}
