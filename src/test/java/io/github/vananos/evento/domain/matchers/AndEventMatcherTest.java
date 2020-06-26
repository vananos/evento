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

class AndEventMatcherTest {

    @ParameterizedTest
    @MethodSource("matchesTestCaseProvider")
    public void matches_shouldMatchAsExpected(boolean firstMatcherResult, boolean secondMatcherResult, boolean expectedResult) {
        // given
        EventMatcher<Event> firstMatcherMock = mock(EventMatcher.class);
        EventMatcher<Event> secondMatcherMock = mock(EventMatcher.class);
        // and
        EventMatcher<Event> andMatcher = new AndEventMatcher<>(firstMatcherMock, secondMatcherMock);
        // and
        when(firstMatcherMock.matches(any())).thenReturn(firstMatcherResult);
        when(secondMatcherMock.matches(any())).thenReturn(secondMatcherResult);
        // when
        boolean actualResult = andMatcher.matches(new Event());
        // then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> matchesTestCaseProvider() {
        return Stream.of(
                Arguments.of(true, true, true),
                Arguments.of(true, false, false),
                Arguments.of(false, true, false),
                Arguments.of(false, false, false));
    }
}