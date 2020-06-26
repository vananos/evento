package io.github.vananos.lamia.httpmatcher;

import io.github.vananos.evento.domain.matchers.EventMatcher;
import io.github.vananos.lamia.HttpEvent;

public class MethodMatcher implements EventMatcher<HttpEvent> {

    public static EventMatcher<HttpEvent> method(String method) {
        return new MethodMatcher(method);
    }

    private final String expectedMethod;

    public MethodMatcher(String expectedMethod) {
        this.expectedMethod = expectedMethod;
    }

    @Override
    public boolean matches(HttpEvent event) {
        return event.getRequest().getMethod().equals(expectedMethod);
    }

    @Override
    public String toString() {
        return "method == " + expectedMethod;
    }
}
