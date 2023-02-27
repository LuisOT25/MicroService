package com.APIrest.app.controllers;

import com.APIrest.app.dtos.Token;
import com.APIrest.app.entitys.Consulta;
import com.APIrest.app.repositorys.RepoConsultas;
import com.APIrest.app.services.AutenticadorSpotify;
import com.APIrest.app.services.SpotifyService;
import com.APIrest.app.services.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Controller {

    private final WeatherService weatherService;
    private final RepoConsultas repoConsultas;
    private final SpotifyService spotifyService;
    private final AutenticadorSpotify autenticadorSpotify;

    public Controller(WeatherService weatherService,
                      RepoConsultas repoConsultas, SpotifyService spotifyService,
                      AutenticadorSpotify autenticadorSpotify) {
        this.weatherService = weatherService;
        this.repoConsultas = repoConsultas;
        this.spotifyService = spotifyService;
        this.autenticadorSpotify = autenticadorSpotify;
    }


    @GetMapping(value="API", params={"city"})
    public ResponseEntity<Object> requestByName(@RequestParam("city") String city){
        try {
            Consulta data = this.weatherService.findByName(city);
            return ResponseEntity.ok(getListTracks(data));
        }catch (RuntimeException runEx){
            return ResponseEntity.badRequest().body(runEx.getMessage());
        }
    }

    private Object getListTracks(Consulta data){
        try {
            Token token = this.autenticadorSpotify.validarToken();
            this.repoConsultas.save(data);
            return this.spotifyService.getList(data.getGenero(),token);
        }catch (JsonProcessingException ex){
            return ResponseEntity.badRequest().body("Hubo un error en el proceso");
        }
    }

    @GetMapping(value="API", params={"lat","lon"})
    public ResponseEntity<Object> requestByCoordinates(@RequestParam("lat")String latitud,
                                          @RequestParam("lon")String longitud){
        try{
            Consulta data = this.weatherService.findByCoordinates(latitud, longitud);
            return ResponseEntity.ok(getListTracks(data));
        }catch (RuntimeException runEx){
            return ResponseEntity.badRequest().body(runEx.getMessage());
        }
    }
}

