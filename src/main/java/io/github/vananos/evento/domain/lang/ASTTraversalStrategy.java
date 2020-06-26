package io.github.vananos.evento.domain.lang;

interface ASTTraversalStrategy {
    void startFrom(ASTNode<?> root);
}
