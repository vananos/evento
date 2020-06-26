package io.github.vananos.evento.domain.lang;

public interface EventHandler<E> {
    void handle(EventContext<E> eventContext);
}
