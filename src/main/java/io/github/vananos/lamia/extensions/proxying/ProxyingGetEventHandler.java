package io.github.vananos.lamia.extensions.proxying;

import io.github.vananos.evento.domain.lang.EventContext;
import io.github.vananos.evento.domain.lang.EventHandler;
import io.github.vananos.lamia.HttpEvent;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * That implementation seems to be dirty, it is not fair "proxying" because here we proxy only body (but for test task it seems to be enough)
 */
public class ProxyingGetEventHandler implements EventHandler<HttpEvent> {
    private final static int BUFF_SIZE = 16384;

    public static ProxyingGetEventHandler proxyTo(Function<HttpServletRequest, String> requestMapper) {
        return new ProxyingGetEventHandler(requestMapper, SimpleURLConnectorImpl::new);
    }

    private final Function<HttpServletRequest, String> requestMapper;
    private final ConnectorsFactory connectorsFactory;

    public ProxyingGetEventHandler(Function<HttpServletRequest, String> requestMapper, ConnectorsFactory connectorsFactory) {
        requireNonNull(requestMapper);
        requireNonNull(connectorsFactory);
        this.requestMapper = requestMapper;
        this.connectorsFactory = connectorsFactory;
    }

    @Override
    public void handle(EventContext<HttpEvent> eventContext) {
        try {
            URL url = new URL(requestMapper.apply(eventContext.getEvent().getRequest()));
            Connector connector = connectorsFactory.getConnectorFor(url);
            byte[] buffer = new byte[BUFF_SIZE];

            try (InputStream is = connector.connect();
                 OutputStream os = eventContext.getEvent().getResponse().getOutputStream()) {
                int read;
                while ((read = is.read(buffer)) > 0) {
                    os.write(buffer, 0, read);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "GET proxying handler";
    }
}
