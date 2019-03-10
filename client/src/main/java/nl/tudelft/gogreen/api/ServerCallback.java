package nl.tudelft.gogreen.api;

<<<<<<< HEAD
import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Data;
=======
import com.mashape.unirest.http.HttpResponse;
import lombok.Data;
import lombok.NonNull;
import nl.tudelft.gogreen.cache.Request;
>>>>>>> dev

import java.util.concurrent.atomic.AtomicBoolean;

@Data
<<<<<<< HEAD
public abstract class ServerCallback<T> {
    private T result;
    private HttpResponse response;
    private AtomicBoolean cancelled;
    private AtomicBoolean failed;
    private UnirestException exception;
=======
public abstract class ServerCallback<T, I> {
    private Request<T> request;
    private I result;
    private HttpResponse response;
    private AtomicBoolean cancelled;
    private AtomicBoolean failed;
    private Exception exception;
    private boolean cached;
>>>>>>> dev

    public ServerCallback() {
        cancelled = new AtomicBoolean(false);
        failed = new AtomicBoolean(false);
    }

<<<<<<< HEAD
=======
    /**
     * <p>Cancels the request. The callback will not be ran.</p>
     */
>>>>>>> dev
    public void cancel() {
        cancelled.set(true);
    }

<<<<<<< HEAD
    protected synchronized void fail(UnirestException exception) {
=======
    protected synchronized void fail(Exception exception) {
>>>>>>> dev
        failed.set(true);
        this.exception = exception;
    }

<<<<<<< HEAD
    protected synchronized void result(T result, HttpResponse response) {
        this.result = result;
        this.response = response;
    }

=======
    protected synchronized void result(@NonNull I result,
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
>>>>>>> dev
    protected int getStatusCode() {
        return response.getStatus();
    }

<<<<<<< HEAD
    protected Headers getHeaders() {
        return response.getHeaders();
=======
    /**
     * <p>Clears the cache for this request.</p>
     */
    protected void clearCache() {
        request.clearCache();
>>>>>>> dev
    }

    public abstract void run();
}
