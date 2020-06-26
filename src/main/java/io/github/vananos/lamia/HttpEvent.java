package io.github.vananos.lamia;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class HttpEvent {
    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public HttpEvent(HttpServletRequest request, HttpServletResponse response) {
        requireNonNull(request);
        requireNonNull(response);
        this.request = request;
        this.response = response;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HttpEvent)) return false;
        HttpEvent httpEvent = (HttpEvent) o;
        return Objects.equals(request, httpEvent.request) &&
                Objects.equals(response, httpEvent.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, response);
    }

    @Override
    public String toString() {
        return "HttpEvent{" +
                "request=" + request +
                ", response=" + response +
                '}';
    }
}
