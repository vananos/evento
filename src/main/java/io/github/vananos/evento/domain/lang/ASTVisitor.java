package io.github.vananos.evento.domain.lang;

interface ASTVisitor<E> {
    void visit(E node);
}
