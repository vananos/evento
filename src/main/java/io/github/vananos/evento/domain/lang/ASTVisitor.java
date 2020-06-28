package io.github.vananos.evento.domain.lang;

interface ASTVisitor<E extends ASTNode> {
    void visit(E node);
}
