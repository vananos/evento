package io.github.vananos.lamia.proxying;

import io.github.vananos.evento.domain.lang.EventContext;
import io.github.vananos.lamia.HttpEvent;
import io.github.vananos.lamia.extensions.proxying.Connector;
import io.github.vananos.lamia.extensions.proxying.ConnectorsFactory;
import io.github.vananos.lamia.extensions.proxying.ProxyingGetEventHandler;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProxyingGetEventHandlerTest {
    private static final byte[] TEST_MESSAGE_BYTES = "test message".getBytes();

    @Test
    void process_shouldProxyRequest() throws IOException {
        // given
        HttpServletRequest httpServletRequestMock = mock(HttpServletRequest.class);
        HttpServletResponse httpServletResponseMock = mock(HttpServletResponse.class);
        Connector connectorMock = mock(Connector.class);
        ConnectorsFactory connectorsFactoryMock = mock(ConnectorsFactory.class);
        // and
        when(connectorsFactoryMock.getConnectorFor(any())).thenReturn(connectorMock);
        // and
        when(httpServletRequestMock.getRequestURI()).thenReturn("/part1/part2");
        when(httpServletRequestMock.getQueryString()).thenReturn("var1=value1");
        when(connectorMock.connect()).thenReturn(new ByteArrayInputStream(TEST_MESSAGE_BYTES));
        // and
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStreamMock = mock(ServletOutputStream.class);

        doAnswer(answer -> {
            Object[] args = answer.getArguments();
            outputStream.write((byte[]) args[0], (int) args[1], (int) args[2]);
            return null;
        }).when(servletOutputStreamMock).write(any(), anyInt(), anyInt());

        when(httpServletResponseMock.getOutputStream()).thenReturn(servletOutputStreamMock);
        // and
        ProxyingGetEventHandler proxyingGetRequestHandler = new ProxyingGetEventHandler(
                request -> "http://google.com" + request.getRequestURI() + "?" + request.getQueryString() + "&some=key", connectorsFactoryMock);
        // when
        proxyingGetRequestHandler.handle(EventContext.create(new HttpEvent(httpServletRequestMock, httpServletResponseMock)));
        // then
        verify(connectorsFactoryMock).getConnectorFor(new URL("http://google.com/part1/part2?var1=value1&some=key"));
        // and
        assertThat(outputStream.toByteArray()).containsExactly(TEST_MESSAGE_BYTES);
    }
}