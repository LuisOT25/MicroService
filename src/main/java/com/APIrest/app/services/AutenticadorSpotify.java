package com.APIrest.app.services;

import com.APIrest.app.dtos.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;
import java.util.Objects;

@Component
public class AutenticadorSpotify {

    @Value("${spotify.auth_url:https://accounts.spotify.com/api/token}")
    String authUrl;

    @Value("${spotify.client_id}")
    String clientId;

    @Value("${spotify.client_secret}")
    String clientSecret;

    private Token authToken;


    public Token validarToken() {
        if (this.authToken== null || !this.authToken.doStillWork()) {
            this.authToken = obtenerNuevoAccessToken();
        }
        return authToken;
    }

    private Token obtenerNuevoAccessToken(){
        RestTemplate restTemplate = new RestTemplate();
        String b64Codes = Base64
                .getEncoder()
                .encodeToString((clientId + ":" + clientSecret).getBytes());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        RequestEntity<Object> request = RequestEntity
                .method(HttpMethod.POST,authUrl)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + b64Codes)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(body);
        ResponseEntity<Token> response =restTemplate.postForEntity(authUrl,request, Token.class);
        return Token.registrarToken(Objects.requireNonNull(response.getBody()));
    }

}
