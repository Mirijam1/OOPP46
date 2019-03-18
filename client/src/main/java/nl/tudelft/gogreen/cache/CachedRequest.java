package nl.tudelft.gogreen.cache;

import com.mashape.unirest.http.HttpResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"staleAfter"})
public class CachedRequest<T, I> {
    private @NonNull Request<T> request;
    private @NonNull int timeToLive;
    private long staleAfter;
    private HttpResponse<I> cachedResponse;

    private boolean isStale() {
        return timeToLive != -1 && System.currentTimeMillis() > staleAfter;
    }

    /**
     * <p>Returns the cached response, if any.
     * Will return null if the response is stale or there is no cached response.</p>
     * @return The cached {@link HttpResponse}, if any.
     */
    protected HttpResponse<I> retrieveFromCache() {
        if (isStale()) {
            return null;
        }

        return cachedResponse;
    }

    /**
     * <p>Updates the cached result of this cached request. This will also reset the counter for
     * the cached response.</p>
     * @param response Cached {@link HttpResponse}.
     */
    protected void updateCache(HttpResponse<I> response) {
        this.cachedResponse = response;
        this.staleAfter = System.currentTimeMillis() + timeToLive * 1000;
    }
}
