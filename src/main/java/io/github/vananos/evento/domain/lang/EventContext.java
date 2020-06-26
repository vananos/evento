package io.github.vananos.evento.domain.lang;

public interface EventContext<E> {
    static <E> EventContext<E> create(E event) {
        return new EventContextImpl<>(event);
    }

    E getEvent();

    void stopProcessing();

    boolean isProcessingStopped();
}
