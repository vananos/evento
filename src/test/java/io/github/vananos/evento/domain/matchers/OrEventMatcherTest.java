package io.github.vananos.evento.domain.matchers;

import io.github.vananos.evento.domain.Event;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrEventMatcherTest {

    @ParameterizedTest
    @MethodSource("matchesTestCaseProvider")
    public void matches_shouldMatchAsExpected(boolean firstMatcherResult, boolean secondMatcherResult, boolean expectedResult) {
        // given
        EventMatcher<Event> firstMatcherMock = mock(EventMatcher.class);
        EventMatcher<Event> secondMatcherMock = mock(EventMatcher.class);
        // and
        EventMatcher<Event> orMatcher = new OrEventMatcher<>(firstMatcherMock, secondMatcherMock);
        // and
        when(firstMatcherMock.matches(any())).thenReturn(firstMatcherResult);
        when(secondMatcherMock.matches(any())).thenReturn(secondMatcherResult);
        // when
        boolean actualResult = orMatcher.matches(new Event());
        // then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> matchesTestCaseProvider() {
        return Stream.of(
                Arguments.of(true, true, true),
                Arguments.of(false, true, true),
                Arguments.of(true, false, true),
                Arguments.of(false, false, false));
    }
}