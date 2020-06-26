package io.github.vananos.evento.domain.lang;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

class EventProcessorImpl<E> implements EventProcessor<E> {
    private final EventHandlerResolver<E> eventHandlerResolver;

    public EventProcessorImpl(EventHandlerResolver<E> eventHandlerResolver) {
        requireNonNull(eventHandlerResolver);
        this.eventHandlerResolver = eventHandlerResolver;
    }

    public void process(E event) {
        eventHandlerResolver.resolve(event).ifPresent(handler -> handler.handle(new EventContextImpl<>(event)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventProcessorImpl)) return false;
        EventProcessorImpl<?> that = (EventProcessorImpl<?>) o;
        return Objects.equals(eventHandlerResolver, that.eventHandlerResolver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventHandlerResolver);
    }

    @Override
    public String toString() {
        return "EventProcessorImpl{" +
                "eventHandlerResolver=" + eventHandlerResolver +
                '}';
    }
}
