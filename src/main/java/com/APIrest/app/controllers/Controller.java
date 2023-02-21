package com.APIrest.app.controllers;

import com.APIrest.app.dtos.Token;
import com.APIrest.app.entitys.Consulta;
import com.APIrest.app.repositorys.RepoConsultas;
import com.APIrest.app.services.AutenticadorSpotify;
import com.APIrest.app.services.SpotifyService;
import com.APIrest.app.services.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    public Controller(RestTemplate restTemplate, WeatherService weatherService, RepoConsultas repoConsultas, SpotifyService spotifyService, AutenticadorSpotify autenticadorSpotify) {
        this.restTemplate = restTemplate;
        this.weatherService = weatherService;
        this.repoConsultas = repoConsultas;
        this.spotifyService = spotifyService;
        this.autenticadorSpotify = autenticadorSpotify;
    }
    @GetMapping(value="API", params={"city"})
    public List<String> getWeatherByName(@RequestParam("city") String city) throws JsonProcessingException {
        Consulta data = this.weatherService.findByName(restTemplate,city);
        this.repoConsultas.save(data);
        double temp = data.getTemp();
        String genero = tempToGender(temp);
        String token = this.autenticadorSpotify.validarToken().getAccessToken();
        return this.spotifyService.getTracks(genero,token);
    }
    @GetMapping(value="API", params={"lat","lon"})
    public List<String> getWeatherByCoordinates(@RequestParam("lat")String latitud,
                                          @RequestParam("lon")String longitud) throws JsonProcessingException {
        double temp = this.weatherService.findByCoordinates(restTemplate,latitud,longitud);
        String genero = tempToGender(temp);
        String token = this.autenticadorSpotify.validarToken().getAccessToken();
        return this.spotifyService.getTracks(genero,token);
    }
    private String tempToGender (double temp){
        if (temp<10.0){return "classical";}
        if (temp<15.0){return "rock";}
        if (temp<30.0){return "pop";}
        return "party";
    }
    @GetMapping("token")

    public Token verToken(){
        Token seeToken = this.autenticadorSpotify.validarToken();
        return seeToken;
    }
}
