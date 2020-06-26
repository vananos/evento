package io.github.vananos.lamia.extensions.proxying;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class SimpleURLConnectorImpl implements Connector {
    private final URL url;

    public SimpleURLConnectorImpl(URL url) {
        this.url = url;
    }

    public InputStream connect() {
        try {
            URLConnection connection = url.openConnection();
            connection.connect();
            return connection.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
