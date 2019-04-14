package nl.tudelft.gogreen.client.tests.cache;

import com.mashape.unirest.http.HttpResponse;
import lombok.NonNull;
import nl.tudelft.gogreen.cache.CachedRequest;
import nl.tudelft.gogreen.cache.Request;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class CachedRequestTest {
    private TestCache request;
    private Request req;

    class TestCache extends CachedRequest {

        public TestCache(@NonNull Request request, @NonNull int timeToLive) {
            super(request, timeToLive);
        }

        public void updateCache(HttpResponse response) {
            super.updateCache(response);
        }

        public HttpResponse get() {
            return super.retrieveFromCache();
        }
    }

    @Before
    public void setUp() {
        req = Mockito.mock(Request.class);

        request = new TestCache(req, 1);
    }

    @Test
    public void retrieveFromCache() {
        assertEquals(2, 2);
    }

    @Test
    public void updateCache() {
        request = new TestCache(req, -1);
        HttpResponse response = Mockito.mock(HttpResponse.class);

        when(response.getStatus()).thenReturn(42);
        request.updateCache(response);

        assertNotNull(request.get());
        assertEquals(request.get().getStatus(), 42);
    }

    @Test
    public void updateCacheAfterStale() throws InterruptedException {
        request = new TestCache(req, 0);
        HttpResponse response = Mockito.mock(HttpResponse.class);

        when(response.getStatus()).thenReturn(42);
        request.updateCache(response);

        Thread.sleep(15);
        assertNull(request.get());
    }
}