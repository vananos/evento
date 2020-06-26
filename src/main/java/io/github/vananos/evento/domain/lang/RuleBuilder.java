package io.github.vananos.evento.domain.lang;

import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;

public interface RuleBuilder<E> extends ASTNode<E> {
    // seems to be useful in future
    RuleBuilder<E> withName(String name);

    RuleBuilder<E> apply(EventHandler<E> handler);

    List<EventHandler<E>> getEventHandlers();

    @Override
    default Collection<RuleBuilder<E>> getChildren() {
        return emptyList();
    }

    String getName();
}
