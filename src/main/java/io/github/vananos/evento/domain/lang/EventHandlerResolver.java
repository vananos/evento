package io.github.vananos.evento.domain.lang;

import java.util.Optional;

public interface EventHandlerResolver<E> {
    Optional<? extends EventHandler<E>> resolve(E event);
}
