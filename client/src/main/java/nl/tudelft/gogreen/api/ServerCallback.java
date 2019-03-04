package nl.tudelft.gogreen.api;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Data;
import nl.tudelft.gogreen.api.servermodels.Category;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@Data
public abstract class ServerCallback<T> {
    private T result;
    private HttpResponse response;
    private AtomicBoolean cancelled;
    private AtomicBoolean failed;
    private UnirestException exception;

    public ServerCallback() {
        cancelled = new AtomicBoolean(false);
        failed = new AtomicBoolean(false);
    }

    public void cancel() {
        cancelled.set(true);
    }

    protected synchronized void fail(UnirestException exception) {
        failed.set(true);
        this.exception = exception;
    }

    protected synchronized void result(T result, HttpResponse response) {
        this.result = result;
        this.response = response;
    }

    protected int getStatusCode() {
        return response.getStatus();
    }

    protected Headers getHeaders() {
        return response.getHeaders();
    }

    public abstract void run();
}
