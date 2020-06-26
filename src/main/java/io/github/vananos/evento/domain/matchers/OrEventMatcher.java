package io.github.vananos.evento.domain.matchers;

import static java.util.Objects.requireNonNull;

public class OrEventMatcher<E> implements EventMatcher<E> {
    public static <E> EventMatcher<E> or(EventMatcher<E> firstMatcher, EventMatcher<E> secondMatcher) {
        return new OrEventMatcher<>(firstMatcher, secondMatcher);
    }

    private final EventMatcher<E> firstMatcher;
    private final EventMatcher<E> secondMatcher;

    public OrEventMatcher(EventMatcher<E> firstMatcher, EventMatcher<E> secondMatcher) {
        requireNonNull(firstMatcher);
        requireNonNull(secondMatcher);
        this.firstMatcher = firstMatcher;
        this.secondMatcher = secondMatcher;
    }

    @Override
    public boolean matches(E event) {
        return firstMatcher.matches(event) || secondMatcher.matches(event);
    }

    @Override
    public String toString() {
        return "(" + firstMatcher.toString() + " or " + secondMatcher.toString() + ")";
    }
}
