package io.github.vananos.evento.domain.lang;

public interface EventProcessor<E> {
    void process(E event);
}
