package nl.tudelft.gogreen.cache;

import com.mashape.unirest.http.HttpResponse;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

public class RequestCache {
    private static RequestCache singletonInstance;
    private MultiValuedMap<String, CachedRequest> cachedRequests;

    private RequestCache() {
        this.cachedRequests = new ArrayListValuedHashMap<>();
    }

    /**
     * <p>Returns the {@link RequestCache} singleton.</p>
     * @return The {@link RequestCache} singleton
     */
    public static RequestCache getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new RequestCache();
        }

        return singletonInstance;
    }

    /**
     * <p>Returns the saved {@link HttpResponse} for the given {@link Request}, if any.</p>
     * @param request The {@link Request} to retrieve from cache
     * @param <T> Type of object inside request
     * @return Cached {@link HttpResponse} if any
     */
    public <T> HttpResponse<T> retrieveFromCache(Request<T> request) {
        for (CachedRequest cachedRequest : cachedRequests.get(request.getUrl())) {
            if (cachedRequest.getRequest().equals(request)) {
                return ((CachedRequest<T>) cachedRequest).retrieveFromCache();
            }
        }

        return null;
    }

    /**
     * <p>Updates the cache (or inserts if no previous request was made) for the given {@link Request}.</p>
     * @param request The {@link Request} to save
     * @param response The {@link HttpResponse} to update or insert
     * @param ttl Cache's time to live
     * @param <T> Type of the object in the request and response
     */
    public <T> void updateCache(Request<T> request, HttpResponse<T> response, int ttl) {
        for (CachedRequest cachedRequest : cachedRequests.get(request.getUrl())) {
            if (cachedRequest.getRequest().equals(request)) {
                ((CachedRequest<T>) cachedRequest).updateCache(response);
                return;
            }
        }

        CachedRequest<T> cachedRequest = new CachedRequest<>(request, ttl);

        cachedRequest.updateCache(response);
        cachedRequests.put(request.getUrl(), cachedRequest);
    }

    /**
     * <p>Clears the entire cache.</p>
     */
    public void clearCache() {
        cachedRequests.clear();
    }

    /**
     * <p>Clears the cached response of the given {@link Request}.</p>
     * @param request {@link Request} to clear the response of
     * @param <T> Type of object stored in {@link Request}
     */
    public <T> void clearCache(Request<T> request) {
        updateCache(request, null, -1);
    }
}
