package io.github.vananos.evento.domain.lang;

import static java.util.Objects.requireNonNull;

public class DFSTraversalStrategy implements ASTTraversalStrategy {
    private final ASTVisitor visitor;

    public static ASTTraversalStrategy usingDFS(ASTVisitor<?> visitor) {
        return new DFSTraversalStrategy(visitor);
    }

    public DFSTraversalStrategy(ASTVisitor<?> visitor) {
        requireNonNull(visitor);
        this.visitor = visitor;
    }

    @Override
    public void startFrom(ASTNode<?> root) {
        root.getChildren().forEach(this::startFrom);
        visitor.visit(root);
    }
}
