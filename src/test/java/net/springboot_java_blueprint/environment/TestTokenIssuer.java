package net.springboot_java_blueprint.environment;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.function.UnaryOperator;

public class TestTokenIssuer implements AutoCloseable {
    private static final String CERTS_PATH = "/certs";

    private final String issuer;
    private final String audience;
    private final RSAKey signingKey = generateKey();
    private final RSAKey foreignKey = generateKey();
    private final HttpServer jwkSetServer;

    public TestTokenIssuer(String issuer, String audience) {
        this.issuer = issuer;
        this.audience = audience;
        this.jwkSetServer = startJwkSetServer(new JWKSet(signingKey.toPublicJWK()).toString());
    }

    public String jwkSetUri() {
        return "http://localhost:" + jwkSetServer.getAddress().getPort() + CERTS_PATH;
    }

    public String issue(TestUser user) {
        return issue(user, UnaryOperator.identity());
    }

    public String issue(TestUser user, UnaryOperator<JWTClaimsSet.Builder> tamper) {
        return sign(tamper.apply(claims(user)).build(), signingKey);
    }

    public String issueWithForeignKey(TestUser user) {
        return sign(claims(user).build(), foreignKey);
    }

    @Override
    public void close() {
        jwkSetServer.stop(0);
    }

    private JWTClaimsSet.Builder claims(TestUser user) {
        var now = Instant.now();
        return new JWTClaimsSet.Builder()
                .issuer(issuer)
                .audience(audience)
                .subject(user.id().toString())
                .claim("preferred_username", user.userName())
                .claim("roles", user.roles())
                .issueTime(Date.from(now))
                .expirationTime(Date.from(now.plus(Duration.ofMinutes(5))));
    }

    private static String sign(JWTClaimsSet claims, RSAKey key) {
        var jwt = new SignedJWT(new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(key.getKeyID()).build(), claims);
        try {
            jwt.sign(new RSASSASigner(key));
        } catch (JOSEException exception) {
            throw new IllegalStateException("Could not sign the test token.", exception);
        }
        return jwt.serialize();
    }

    private static RSAKey generateKey() {
        try {
            return new RSAKeyGenerator(2048).keyID(UUID.randomUUID().toString()).generate();
        } catch (JOSEException exception) {
            throw new IllegalStateException("Could not generate the test signing key.", exception);
        }
    }

    private static HttpServer startJwkSetServer(String jwkSet) {
        try {
            var server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
            var body = jwkSet.getBytes(StandardCharsets.UTF_8);
            server.createContext(CERTS_PATH, exchange -> {
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, body.length);
                try (var response = exchange.getResponseBody()) {
                    response.write(body);
                }
            });
            server.start();
            return server;
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }
}
