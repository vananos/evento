package io.github.vananos.lamia.extensions.auth;

import io.github.vananos.evento.domain.lang.EventContext;
import io.github.vananos.evento.domain.lang.EventHandler;
import io.github.vananos.lamia.HttpEvent;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Key;

/**
 * All this package is dirty and not secure authorization, because I haven't found information about
 * how authorization should work in description, therefore I implemented the simplest model when everyone who
 * has token is authorized, and everyone who knows address can get the token
 */
public class AuthorizationHandler implements EventHandler<HttpEvent> {
    public static EventHandler<HttpEvent> mustBeAuthorized() {
        return new AuthorizationHandler();
    }

    @Override
    public void handle(EventContext<HttpEvent> eventContext) {
        HttpServletRequest request = eventContext.getEvent().getRequest();
        HttpServletResponse response = eventContext.getEvent().getResponse();
        try {
            String header = request.getHeader("Authorization");
            if (header == null) {
                // without token
                reject(response, eventContext);
                return;
            }
            String compactJws = header.replace("Bearer ", "");
            Key key = SecretHolder.getSecret();
            Jwts.parserBuilder()
                    .setSigningKey(key).build().parseClaimsJws(compactJws);
        } catch (JwtException e) {
            // invalid token
            reject(response, eventContext);
        }

    }

    @Override
    public String toString() {
        return "JWT based authorization handler";
    }

    private void reject(HttpServletResponse response, EventContext eventContext) {
        response.setStatus(401);
        try (PrintWriter writer = response.getWriter()) {
            writer.println("Authentication required");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            eventContext.stopProcessing();
        }
    }
}
