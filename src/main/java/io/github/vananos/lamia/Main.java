package io.github.vananos.lamia;

import io.github.vananos.evento.domain.lang.EventHandlerResolverFactory;
import io.github.vananos.evento.domain.lang.EventProcessor;
import io.github.vananos.evento.domain.lang.EventProcessorBuilder;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static io.github.vananos.evento.domain.lang.ConcreteRuleBuilderImpl.when;
import static io.github.vananos.evento.domain.lang.GroupRuleBuilderImpl.groupOf;
import static io.github.vananos.lamia.extensions.auth.AuthorizationHandler.mustBeAuthorized;
import static io.github.vananos.lamia.extensions.auth.LoginHandler.authorizeUsingJWTHeader;
import static io.github.vananos.lamia.extensions.proxying.ProxyingGetEventHandler.proxyTo;
import static io.github.vananos.lamia.httpmatcher.MethodMatcher.method;
import static io.github.vananos.lamia.httpmatcher.UriMatcher.uriStartsWith;
import static java.util.stream.Collectors.joining;

/**
 *
 */
public class Main {
    private static final int PORT = Integer.parseInt(System.getProperty("server.port", "8080"));
    private static final String API_KEY = System.getProperty("api.key", "ea98686");

    private static final EventProcessor<HttpEvent> eventProcessor = EventProcessorBuilder
            .processorFor(HttpEvent.class)
            .rule(when(method("POST").and(uriStartsWith("/login"))).apply(authorizeUsingJWTHeader()))
            .rule(groupOf(HttpEvent.class).withName("authorization required group")
                    .apply(mustBeAuthorized())
                    .rule(when(method("GET")
                            .and(uriStartsWith("/getMovie")))
                            .apply(proxyTo(req -> "http://www.omdbapi.com/?" +
                                    req.getParameterMap()
                                            .entrySet()
                                            .stream()
                                            .map(e -> e.getKey() + "=" + String.join(",", e.getValue()))
                                            .collect(joining("&"))
                                    + "&apikey=" + API_KEY)))
                    .rule(when(method("GET")
                            .and(uriStartsWith("/getBook")))
                            .apply(proxyTo(req -> "https://openlibrary.org/api/books?bibkeys=" +
                                    req.getParameter("isbn") + "&format=json"))))
            .withHandlerResolvingStrategy(EventHandlerResolverFactory.linearSearchResolver())
            .build();

    public static void main(String[] args) throws Exception {
        Server server = new Server(PORT);
        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) {
                eventProcessor.process(new HttpEvent(request, response));
                baseRequest.setHandled(true);
            }
        });
        server.start();
        server.join();
    }
}