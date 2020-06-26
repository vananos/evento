package io.github.vananos.evento.domain;

import io.github.vananos.evento.domain.lang.EventHandler;
import io.github.vananos.evento.domain.matchers.EventMatcher;

import static org.mockito.Mockito.mock;

public class Constants {
    public static final EventMatcher<Event> EVENT_MATCHER = mock(EventMatcher.class);
    public static final EventMatcher<Event> EVENT_MATCHER2 = mock(EventMatcher.class);

    public static final EventHandler<Event> DUMMY_HANDLER = mock(EventHandler.class);

    public static final EventHandler<Event> DUMMY_HANDLER2 = mock(EventHandler.class);
}
