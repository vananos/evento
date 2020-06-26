package io.github.vananos.evento.domain.lang;

import java.util.Collection;

import static java.util.Collections.emptyList;

interface ASTNode<E> {

    default Collection<? extends ASTNode<E>> getChildren() {
        return emptyList();
    }
}
