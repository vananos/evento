package io.github.vananos.evento.domain.lang;

import java.util.ArrayList;
import java.util.Collection;

class VisitorsChain {

    public static <N extends ASTNode<?>> VisitorsChainBuilder<N, ASTVisitor<? super N>> chainFor(Class<N> type) {
        return new VisitorsChainBuilder();
    }

    public static class VisitorsChainBuilder<N extends ASTNode<?>, V extends ASTVisitor<? super N>> {
        private final Collection<V> visitors = new ArrayList<>();

        public VisitorsChainBuilder<N, V> apply(V visitor) {
            visitors.add(visitor);
            return this;
        }

        public VisitorsChainBuilder<N, V> then(V visitor) {
            visitors.add(visitor);
            return this;
        }

        public ASTVisitor<N> build() {
            return node -> visitors.forEach(visitor -> visitor.visit(node));
        }
    }
}
