package io.github.vananos.evento.domain.lang;

import io.github.vananos.evento.domain.matchers.EventMatcher;

import java.util.Collection;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class EventHandlersChain<T> implements EventHandler<T>, EventMatcher<T> {
    private final EventMatcher<T> eventMatcher;
    private final Collection<? extends EventHandler<T>> handlers;

    public EventHandlersChain(EventMatcher<T> eventMatcher, Collection<? extends EventHandler<T>> handlers) {
        requireNonNull(eventMatcher);
        requireNonNull(handlers);
        this.eventMatcher = eventMatcher;
        this.handlers = handlers;
    }

    @Override
    public boolean matches(T event) {
        return eventMatcher.matches(event);
    }

    @Override
    public void handle(EventContext<T> eventContext) {
        for (EventHandler<T> handler : handlers) {
            handler.handle(eventContext);
            if (eventContext.isProcessingStopped()) {
                return;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventHandlersChain)) return false;
        EventHandlersChain<?> that = (EventHandlersChain<?>) o;
        return Objects.equals(eventMatcher, that.eventMatcher) &&
                Objects.equals(handlers, that.handlers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventMatcher, handlers);
    }

    @Override
    public String toString() {
        return "EventHandlersChain{" +
                "eventMatcher=" + eventMatcher +
                ", handlers=" + handlers +
                '}';
    }
}
