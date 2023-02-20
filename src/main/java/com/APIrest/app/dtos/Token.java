package com.APIrest.app.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

public class Token{

    private static final Logger LOGGER = LoggerFactory.getLogger(Token.class);
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private Integer expiresIn;

    private Instant momentoExpiracion;

    public static Token registrarToken(Token token) {
        LOGGER.trace("registrarToken({})", token.tokenType);
        Token nuevo = new Token();
        nuevo.accessToken = token.getAccessToken();
        nuevo.tokenType = token.getTokenType();
        nuevo.expiresIn = token.expiresIn;
        nuevo.calcularMomentoExpiracion();
        return nuevo;
    }

    public void calcularMomentoExpiracion() {
        if (this.expiresIn == 0) this.expiresIn = 3600;
        this.momentoExpiracion = Instant.now()
                .plusSeconds(this.expiresIn)
                .minusSeconds(60);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public boolean isExpired() {
        return this.momentoExpiracion.isAfter(Instant.now());
    }

}
