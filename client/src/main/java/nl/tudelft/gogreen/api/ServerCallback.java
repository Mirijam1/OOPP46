package nl.tudelft.gogreen.api;

import com.mashape.unirest.http.HttpResponse;
import lombok.Data;
import lombok.NonNull;
import nl.tudelft.gogreen.cache.Request;

import java.util.concurrent.atomic.AtomicBoolean;

@Data
public abstract class ServerCallback<T> {
    private Request<T> request;
    private T result;
    private HttpResponse response;
    private AtomicBoolean cancelled;
    private AtomicBoolean failed;
    private Exception exception;
    private boolean cached;

    public ServerCallback() {
        cancelled = new AtomicBoolean(false);
        failed = new AtomicBoolean(false);
    }

    /**
     * <p>Cancels the request. The callback will not be ran.</p>
     */
    public void cancel() {
        cancelled.set(true);
    }

    protected synchronized void fail(Exception exception) {
        failed.set(true);
        this.exception = exception;
    }

    protected synchronized void result(@NonNull T result,
                                       @NonNull HttpResponse response,
                                       @NonNull boolean cached,
                                       @NonNull Request<T> request) {
        this.result = result;
        this.response = response;
        this.cached = cached;
        this.request = request;
    }

    /**
     * <p>Returns the status code of the previous request.</p>
     * @return Integer representing the status code of the previous request
     */
    protected int getStatusCode() {
        return response.getStatus();
    }

    /**
     * <p>Clears the cache for this request.</p>
     */
    protected void clearCache() {
        request.clearCache();
    }

    public abstract void run();
}
