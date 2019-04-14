package nl.tudelft.gogreen.client.tests.cache;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import nl.tudelft.gogreen.cache.Request;
import nl.tudelft.gogreen.cache.RequestCache;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RequestCacheTest {
    private RequestCache cache;

    @Before
    public void setUp() {
        cache = RequestCache.getInstance();
    }

    @Test
    public void shouldReturnCachedRequest() {
        HttpResponse response = Mockito.mock(HttpResponse.class);
        Request request = new Request(HttpMethod.GET, "s");
        Request request2 = new Request(HttpMethod.GET, "s2");
        when(response.getStatus()).thenReturn(42);
        cache.updateCache(request, response, 1);
        cache.updateCache(request2, response, 1);

        assertNotNull(cache.retrieveFromCache(request));
        assertEquals(42, cache.retrieveFromCache(request).getStatus());
    }

    @Test
    public void shouldUpdateExistingRecord() {
        HttpResponse response = Mockito.mock(HttpResponse.class);
        HttpResponse response2 = Mockito.mock(HttpResponse.class);
        Request request = new Request(HttpMethod.GET, "s");
        Request request2 = new Request(HttpMethod.GET, "s2");
        when(response.getStatus()).thenReturn(42);
        when(response2.getStatus()).thenReturn(4242);
        cache.updateCache(request2, response, 1);
        cache.updateCache(request, response, 1);
        cache.updateCache(request, response2, 1);

        assertNotNull(cache.retrieveFromCache(request));
        assertEquals(4242, cache.retrieveFromCache(request).getStatus());
    }

    @Test
    public void shouldClearCache() {
        HttpResponse response = Mockito.mock(HttpResponse.class);
        Request request = new Request(HttpMethod.GET, "s");
        when(response.getStatus()).thenReturn(42);
        cache.updateCache(request, response, 1);


        cache.clearCache(request);
        assertNull(cache.retrieveFromCache(request));
    }

    @Test
    public void shouldClearEntireCache() {
        HttpResponse response = Mockito.mock(HttpResponse.class);
        Request request = new Request(HttpMethod.GET, "s");
        Request request2 = new Request(HttpMethod.GET, "s2");
        when(response.getStatus()).thenReturn(42);
        cache.updateCache(request, response, 1);
        cache.updateCache(request2, response, 1);


        cache.clearCache();
        assertNull(cache.retrieveFromCache(request));
        assertNull(cache.retrieveFromCache(request2));
    }
}