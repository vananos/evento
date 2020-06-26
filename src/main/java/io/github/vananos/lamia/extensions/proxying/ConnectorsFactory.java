package io.github.vananos.lamia.extensions.proxying;

import java.net.URL;

public interface ConnectorsFactory {
    Connector getConnectorFor(URL url);
}
