package io.github.vananos.lamia.extensions.auth;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

/**
 * All this package is dirty and not secure authorization, because I haven't found information about
 * how authorization should work in description, therefore I implemented the simplest model when everyone who
 * has token is authorized, and everyone who knows address can get the token
 */
public class SecretHolder {
    private static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static Key getSecret() {
        return KEY;
    }
}
