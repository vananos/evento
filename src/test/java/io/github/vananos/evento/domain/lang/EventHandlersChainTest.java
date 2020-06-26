package io.github.vananos.evento.domain.lang;

import io.github.vananos.evento.domain.Event;
import io.github.vananos.evento.domain.matchers.EventMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventHandlersChainTest {

    private Event event = new Event();
    private EventContext<Event> eventContextMock;

    @BeforeEach
    public void setUp() {
        eventContextMock = mock(EventContext.class);
    }

    @Test
    public void matches_shouldDelegateToUnderliningMatcher() {
        // given
        EventMatcher<Event> eventMatcherMock = mock(EventMatcher.class);
        EventHandlersChain<Event> eventHandlersChain = new EventHandlersChain<>(eventMatcherMock, Collections.emptyList());
        // and
        when(eventMatcherMock.matches(any())).thenReturn(true);
        // when
        boolean result = eventHandlersChain.matches(event);
        // then
        assertThat(result).isTrue();
        // and
        verify(eventMatcherMock).matches(event);
    }

    @Test
    public void process_shouldCallAllHandlers() {
        // given
        Collection<EventHandler<Event>> handlers = asList(
                mock(EventHandler.class),
                mock(EventHandler.class),
                mock(EventHandler.class));
        // and
        EventHandlersChain<Event> eventHandlersChain = new EventHandlersChain<>(mock(EventMatcher.class), handlers);
        // when
        eventHandlersChain.handle(eventContextMock);
        // then
        handlers.forEach(handler -> verify(handler).handle(eventContextMock));
    }

    @Test
    public void process_shouldStopProcessingWhenStopProcessingWasCalled() {
        // given
        List<EventHandler<Event>> handlers = asList(
                mock(EventHandler.class),
                mock(EventHandler.class));
        // and
        EventHandlersChain<Event> eventHandlersChain = new EventHandlersChain<>(mock(EventMatcher.class), handlers);
        // and
        when(eventContextMock.isProcessingStopped()).thenReturn(true);
        // when
        eventHandlersChain.handle(eventContextMock);
        // then
        verify(handlers.get(0)).handle(eventContextMock);
        // and
        verify(handlers.get(1), never()).handle(any());
    }
}