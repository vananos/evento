package io.github.vananos.lamia.extensions.proxying;

import java.io.InputStream;

public interface Connector {
    InputStream connect();
}
