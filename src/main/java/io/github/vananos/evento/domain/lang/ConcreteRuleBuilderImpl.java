package io.github.vananos.evento.domain.lang;

import io.github.vananos.evento.domain.matchers.EventMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class ConcreteRuleBuilderImpl<E> implements RuleBuilder<E> {
    public static <E> ConcreteRuleBuilderImpl<E> when(EventMatcher<E> eventMatcher) {
        return new ConcreteRuleBuilderImpl<>(eventMatcher);
    }

    private final EventMatcher<E> eventMatcher;
    private final List<EventHandler<E>> eventHandlers = new ArrayList<>();
    private String name;

    protected ConcreteRuleBuilderImpl(EventMatcher<E> eventMatcher) {
        requireNonNull(eventMatcher);
        this.eventMatcher = eventMatcher;
    }

    @Override
    public RuleBuilder<E> withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public RuleBuilder<E> apply(EventHandler<E> eventHandler) {
        eventHandlers.add(eventHandler);
        return this;
    }

    @Override
    public List<EventHandler<E>> getEventHandlers() {
        return eventHandlers;
    }

    @Override
    public String getName() {
        return name;
    }

    public EventMatcher<E> getEventMatcher() {
        return eventMatcher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConcreteRuleBuilderImpl)) return false;
        ConcreteRuleBuilderImpl<?> that = (ConcreteRuleBuilderImpl<?>) o;
        return Objects.equals(eventMatcher, that.eventMatcher) &&
                Objects.equals(getEventHandlers(), that.getEventHandlers()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventMatcher, getEventHandlers(), getName());
    }

    @Override
    public String toString() {
        return "ConcreteRuleBuilderImpl{" +
                "eventMatcher=" + eventMatcher +
                ", eventHandlers=" + eventHandlers +
                ", name='" + name + '\'' +
                '}';
    }
}
