package io.github.vananos.evento.domain.matchers;

public interface EventMatcher<E> {
    boolean matches(E event);

    default EventMatcher<E> and(EventMatcher<E> otherMatcher) {
        return new AndEventMatcher<>(this, otherMatcher);
    }

    default EventMatcher<E> or(EventMatcher<E> otherMatcher) {
        return new OrEventMatcher(this, otherMatcher);
    }
}
