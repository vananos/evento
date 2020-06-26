package io.github.vananos.evento.domain.lang;

import static io.github.vananos.evento.domain.lang.DFSTraversalStrategy.usingDFS;
import static io.github.vananos.evento.domain.lang.GroupRuleBuilderImpl.group;
import static io.github.vananos.evento.domain.lang.VisitorsChain.chainFor;
import static java.util.Objects.requireNonNull;

public final class EventProcessorBuilder<E> {
    public static <E> Builder<E> processorFor(Class<E> eventType) {
        return new Builder<>(eventType);
    }

    public static class Builder<E> {
        private final Class<E> type;
        private final GroupRuleBuilderImpl<E> defaultGroupBuilder;
        private EventHandlerResolverFactory<E> eventHandlerResolverFactoryFactory;

        @SuppressWarnings("unchecked")
        public Builder(Class<E> type) {
            requireNonNull(type);
            this.type = type;
            defaultGroupBuilder = (GroupRuleBuilderImpl<E>) group().withName("#default");
        }

        public Builder<E> rule(RuleBuilder<E> ruleBuilder) {
            defaultGroupBuilder.rule(ruleBuilder);
            return this;
        }

        public Builder<E> useHandler(EventHandler<E> handler) {
            defaultGroupBuilder.apply(handler);
            return this;
        }

        public Builder<E> withHandlerResolvingStrategy(EventHandlerResolverFactory<E> eventHandlerResolverFactoryFactory) {
            this.eventHandlerResolverFactoryFactory = eventHandlerResolverFactoryFactory;
            return this;
        }

        @SuppressWarnings("unchecked")
        public EventProcessor<E> build() {
            EventHandlersChainsBuilder chainsBuilder = new EventHandlersChainsBuilder();

            usingDFS(chainFor(RuleBuilder.class)
                    .apply(new DefaultsVisitor())
                    .then(new ValidatorVisitor())
                    .then(chainsBuilder)
                    .build())
                    .startFrom(defaultGroupBuilder);

            EventHandlerResolver<?> eventHandlerResolver = ((EventHandlerResolverFactory) eventHandlerResolverFactoryFactory).build(
                    chainsBuilder.getEventHandlersChains());

            return new EventProcessorImpl(eventHandlerResolver);
        }
    }
}
