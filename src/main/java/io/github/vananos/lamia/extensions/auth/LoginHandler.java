package io.github.vananos.lamia.extensions.auth;

import io.github.vananos.evento.domain.lang.EventContext;
import io.github.vananos.evento.domain.lang.EventHandler;
import io.github.vananos.lamia.HttpEvent;
import io.jsonwebtoken.Jwts;

import java.security.Key;

/**
 * All this package is dirty and not secure authorization, because I haven't found information about
 * how authorization should work in description, therefore I implemented the simplest model when everyone who
 * has token is authorized, and everyone who knows address can get the token
 */
public class LoginHandler implements EventHandler<HttpEvent> {
    public static EventHandler<HttpEvent> authorizeUsingJWTHeader() {
        return new LoginHandler();
    }

    @Override
    public void handle(EventContext<HttpEvent> eventContext) {
        Key key = SecretHolder.getSecret();
        String jws = Jwts.builder().setSubject("registered user").signWith(key).compact();
        eventContext.getEvent().getResponse().setHeader("Authorization", "Bearer " + jws);
        eventContext.stopProcessing();

    }

    @Override
    public String toString() {
        return "login using JWT handler";
    }
}
