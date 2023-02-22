package com.APIrest.app.controllers;

import com.APIrest.app.entitys.Consulta;
import com.APIrest.app.repositorys.RepoConsultas;
import com.APIrest.app.services.AutenticadorSpotify;
import com.APIrest.app.services.SpotifyService;
import com.APIrest.app.services.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class Controller {


    private final RestTemplate restTemplate;
    private final WeatherService weatherService;

    private final RepoConsultas repoConsultas;
    private final SpotifyService spotifyService;
    private final AutenticadorSpotify autenticadorSpotify;

    public Controller(RestTemplate restTemplate, WeatherService weatherService,
                      RepoConsultas repoConsultas, SpotifyService spotifyService,
                      AutenticadorSpotify autenticadorSpotify) {
        this.restTemplate = restTemplate;
        this.weatherService = weatherService;
        this.repoConsultas = repoConsultas;
        this.spotifyService = spotifyService;
        this.autenticadorSpotify = autenticadorSpotify;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @GetMapping(value="API", params={"city"})
    public ResponseEntity<Object> getWeatherByName(@RequestParam("city") String city){
        try {
            Consulta data = this.weatherService.findByName(restTemplate, city);
            return ResponseEntity.ok(getListTracks(data));
        }catch (RuntimeException runEx){
            LOGGER.error("error",runEx);
            return ResponseEntity.badRequest().body(runEx.getMessage());
        }
    }

    private Object getListTracks(Consulta data){
        try {
            String token = this.autenticadorSpotify.validarToken().getAccessToken();
            this.repoConsultas.save(data);
            return this.spotifyService.getTracks(data.getGenero(), token);
        }catch (JsonProcessingException ex){
            return null;
        }
    }

    @GetMapping(value="API", params={"lat","lon"})
    public ResponseEntity<Object> getWeatherByCoordinates(@RequestParam("lat")String latitud,
                                          @RequestParam("lon")String longitud){
        try{
            Consulta data = this.weatherService.findByCoordinates(restTemplate, latitud, longitud);
            return ResponseEntity.ok(getListTracks(data));
        }catch (RuntimeException runEx){
            return ResponseEntity.badRequest().body(runEx.getMessage());
        }
    }
}
