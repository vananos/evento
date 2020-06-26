package io.github.vananos.lamia.httpmatcher;

import io.github.vananos.evento.domain.matchers.EventMatcher;
import io.github.vananos.lamia.HttpEvent;

import static java.util.Objects.requireNonNull;

public class UriMatcher implements EventMatcher<HttpEvent> {

    public static EventMatcher<HttpEvent> uriStartsWith(String expectedStart) {
        return new UriMatcher(expectedStart);
    }

    private final String expectedStart;

    public UriMatcher(String expectedStart) {
        requireNonNull(expectedStart);
        this.expectedStart = expectedStart;
    }

    @Override
    public boolean matches(HttpEvent event) {
        return event.getRequest().getRequestURI().startsWith(expectedStart);
    }

    @Override
    public String toString() {
        return "query starts with '" + expectedStart + "'";
    }

}
