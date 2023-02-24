package com.APIrest.app.services;

import com.APIrest.app.dtos.Token;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class SpotifyService {
    @Value("${spotify.api_base_url}")
    String spotiftyBaseUrl;

    public List<String> classicalList;
    public List<String> popList;
    public List<String> rockList;
    public List<String> partyList;

    public List<String> getList(String genero, Token token)throws JsonProcessingException{
            if (genero.equals("classical")) {
                if (this.classicalList == null|| !token.doStillWork()) {
                    this.classicalList = getTracks(genero, token.getAccessToken());
                }
                return classicalList;
            } else if (genero.equals("pop")|| !token.doStillWork()) {
                if (this.popList == null) {
                    this.popList = getTracks(genero, token.getAccessToken());
                }
                return popList;
            } else if (genero.equals("rock")|| !token.doStillWork()) {
                if (this.rockList == null) {
                    this.rockList = getTracks(genero, token.getAccessToken());
                }
                return rockList;
            } else if (genero.equals("party")|| !token.doStillWork()) {
                if (this.partyList == null) {
                    this.partyList = getTracks(genero, token.getAccessToken());
                }
                return this.partyList;
            }
            return null;
        }


    public List<String> getTracks (String genero, String token) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = spotiftyBaseUrl+"/search?q=gendre:"+genero+"&type=track";
        RequestEntity<Object> request = RequestEntity
                .method(HttpMethod.GET,url)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body("");
        ResponseEntity<String> response = restTemplate.exchange(request, String.class);
        if (!response.getStatusCode().is2xxSuccessful()){return null;}
        ObjectMapper mapper = new ObjectMapper();
        JsonNode items = mapper.readTree(response.getBody())
                .path("tracks")
                .path("items");
        Iterator<JsonNode> it = items.elements();
        List<String> tracks = new ArrayList<>();
        while (it.hasNext()) {
            JsonNode t = it.next();
            tracks.add(t.path("name").asText());
        }
        return tracks;
    }
}
