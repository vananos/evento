package io.github.vananos.evento.domain.lang;

import java.util.Collection;

public interface EventHandlerResolverFactory<E> {
    @SuppressWarnings("unchecked")
    static <E> EventHandlerResolverFactory<E> linearSearchResolver() {
        return rules -> new LinearSearchEventHandlerResolver(rules);
    }

    EventHandlerResolver<E> build(Collection<? extends EventHandlersChain<E>> chainsOfHandlers);
}
