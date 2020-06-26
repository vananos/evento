package io.github.vananos.evento.domain.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class GroupRuleBuilderImpl<E> implements RuleBuilder<E> {
    public static <E> GroupRuleBuilderImpl<E> groupOf(Class<E> event) {
        return new GroupRuleBuilderImpl<>();
    }

    public static <E> GroupRuleBuilderImpl<E> group() {
        return new GroupRuleBuilderImpl<>();
    }

    private final List<EventHandler<E>> handlers = new ArrayList<>();
    private final Collection<RuleBuilder<E>> children = new ArrayList<>();
    private String name;

    @Override
    public GroupRuleBuilderImpl<E> withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public GroupRuleBuilderImpl<E> apply(EventHandler<E> handler) {
        handlers.add(handler);
        return this;
    }

    @Override
    public List<EventHandler<E>> getEventHandlers() {
        return handlers;
    }

    public GroupRuleBuilderImpl<E> rule(RuleBuilder<E> ruleBuilder) {
        requireNonNull(ruleBuilder);
        children.add(ruleBuilder);
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Collection<RuleBuilder<E>> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupRuleBuilderImpl)) return false;
        GroupRuleBuilderImpl<?> that = (GroupRuleBuilderImpl<?>) o;
        return Objects.equals(handlers, that.handlers) &&
                Objects.equals(getChildren(), that.getChildren()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(handlers, getChildren(), getName());
    }

    @Override
    public String toString() {
        return "RuleBuilderImpl{" +
                "handlers=" + handlers +
                ", children=" + children +
                ", name='" + name + '\'' +
                '}';
    }
}