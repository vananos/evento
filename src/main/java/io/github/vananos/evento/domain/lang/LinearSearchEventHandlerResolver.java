package io.github.vananos.evento.domain.lang;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

class LinearSearchEventHandlerResolver<E> implements EventHandlerResolver<E> {
    private final Collection<EventHandlersChain<E>> chainsOfHandlers;

    public LinearSearchEventHandlerResolver(Collection<EventHandlersChain<E>> chainsOfHandlers) {
        requireNonNull(chainsOfHandlers);
        this.chainsOfHandlers = chainsOfHandlers;
    }

    @Override
    public Optional<? extends EventHandler<E>> resolve(E event) {
        return chainsOfHandlers.stream()
                .filter(handler -> handler.matches(event))
                .findFirst();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinearSearchEventHandlerResolver)) return false;
        LinearSearchEventHandlerResolver<?> that = (LinearSearchEventHandlerResolver<?>) o;
        return Objects.equals(chainsOfHandlers, that.chainsOfHandlers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chainsOfHandlers);
    }

    @Override
    public String toString() {
        return "SequentialEventHandlerResolver{" +
                "chainsOfHandlers=" + chainsOfHandlers +
                '}';
    }
}