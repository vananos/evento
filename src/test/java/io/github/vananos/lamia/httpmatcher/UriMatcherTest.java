package io.github.vananos.lamia.httpmatcher;

import io.github.vananos.evento.domain.matchers.EventMatcher;
import io.github.vananos.lamia.HttpEvent;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Stream;

import static io.github.vananos.lamia.httpmatcher.UriMatcher.uriStartsWith;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UriMatcherTest {

    @ParameterizedTest
    @MethodSource("matchesTestCaseProvider")
    void matches_shouldMatchWhenQueryStartsWithExpectedValue(String queryString, boolean expectedResult) {
        // given
        HttpServletRequest requestMock = mock(HttpServletRequest.class);
        HttpServletResponse responseMock = mock(HttpServletResponse.class);
        when(requestMock.getRequestURI()).thenReturn(queryString);
        // and
        EventMatcher<HttpEvent> methodMatcher = uriStartsWith("/getMovie");
        // when
        boolean actualResult = methodMatcher.matches(new HttpEvent(requestMock, responseMock));
        // then
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> matchesTestCaseProvider() {
        return Stream.of(
                Arguments.of("/getMovie?name=terminator", true),
                Arguments.of("/getPorn?name=XXX", false));
    }
}
