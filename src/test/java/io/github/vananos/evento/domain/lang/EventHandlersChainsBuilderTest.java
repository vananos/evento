package io.github.vananos.evento.domain.lang;

import io.github.vananos.evento.domain.Event;
import io.github.vananos.evento.domain.matchers.EventMatcher;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Objects;

import static io.github.vananos.evento.domain.lang.ConcreteRuleBuilderImpl.when;
import static io.github.vananos.evento.domain.lang.DFSTraversalStrategy.usingDFS;
import static io.github.vananos.evento.domain.lang.GroupRuleBuilderImpl.groupOf;
import static io.github.vananos.evento.domain.lang.VisitorsChain.chainFor;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class EventHandlersChainsBuilderTest {

    @Test
    public void build_shouldBuildExpectedEventProcessor() {
        // given
        GroupRuleBuilderImpl<Event> ruleBuilder = groupOf(Event.class);
        EventHandler<Event> firstHandler = namedHandler("first handler");
        EventHandler<Event> secondHandler = namedHandler("second handler");
        EventHandler<Event> thirdHandler = namedHandler("third handler");
        EventHandler<Event> fourthHandler = namedHandler("fourth handler");
        // and
        EventMatcher<Event> firstMatcher = namedMatcher("first");
        EventMatcher<Event> secondMatcher = namedMatcher("second");
        EventMatcher<Event> thirdMatcher = namedMatcher("third");
        // and
        ruleBuilder
                .apply(firstHandler)
                .rule(groupOf(Event.class)
                        .rule(when(firstMatcher).apply(secondHandler))
                        .rule(when(secondMatcher).apply(thirdHandler))
                        .rule(when(thirdMatcher).withName("custom name").apply(fourthHandler))
                        .rule(groupOf(Event.class)
                                .apply(fourthHandler)
                                .rule(when(thirdMatcher).apply(secondHandler))
                             ));
        // and
        EventHandlersChain[] expectedChains = {
                new EventHandlersChain<>(firstMatcher, asList(firstHandler, secondHandler)),
                new EventHandlersChain<>(secondMatcher, asList(firstHandler, thirdHandler)),
                new EventHandlersChain<>(thirdMatcher, asList(firstHandler, fourthHandler)),
                new EventHandlersChain<>(thirdMatcher, asList(firstHandler, fourthHandler, secondHandler))
        };
        // and
        EventHandlersChainsBuilder eventHandlersChainsBuilder = new EventHandlersChainsBuilder();
        // when
        usingDFS(chainFor(RuleBuilder.class)
                .apply(eventHandlersChainsBuilder)
                .build())
                .startFrom(ruleBuilder);
        // and
        Collection<EventHandlersChain<?>> actualChains = eventHandlersChainsBuilder.getEventHandlersChains();
        // then
        assertThat(actualChains).containsExactly(expectedChains);
    }

    private EventMatcher<Event> namedMatcher(String name) {
        return new NamedEntity(name);
    }

    private EventHandler<Event> namedHandler(String name) {
        return new NamedEntity(name);
    }

    private static class NamedEntity implements EventHandler<Event>, EventMatcher<Event> {
        private final String instanceName;

        private NamedEntity(String instanceName) {
            this.instanceName = instanceName;
        }

        @Override
        public void handle(EventContext<Event> eventContext) {

        }

        @Override
        public String toString() {
            return instanceName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof NamedEntity)) return false;
            NamedEntity that = (NamedEntity) o;
            return Objects.equals(instanceName, that.instanceName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(instanceName);
        }

        @Override
        public boolean matches(Event event) {
            return false;
        }
    }
}