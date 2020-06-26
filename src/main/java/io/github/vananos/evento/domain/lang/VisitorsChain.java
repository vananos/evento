package io.github.vananos.evento.domain.lang;

import java.util.ArrayList;
import java.util.Collection;

class VisitorsChain<E extends ASTVisitor<E>> {

    public static <E> VisitorsChainBuilder<E> chainFor(Class<E> type) {
        return new VisitorsChainBuilder<>();
    }

    public static class VisitorsChainBuilder<E> {
        private final Collection<ASTVisitor<E>> visitors = new ArrayList<>();

        public VisitorsChainBuilder<E> apply(ASTVisitor<E> visitor) {
            visitors.add(visitor);
            return this;
        }

        public VisitorsChainBuilder<E> then(ASTVisitor<E> visitor) {
            visitors.add(visitor);
            return this;
        }

        public ASTVisitor<E> build() {
            return node -> visitors.forEach(visitor -> visitor.visit(node));
        }
    }
}
