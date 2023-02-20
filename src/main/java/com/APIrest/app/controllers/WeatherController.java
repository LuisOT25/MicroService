package com.APIrest.app.controllers;

import com.APIrest.app.dtos.Token;
import com.APIrest.app.services.AutenticadorSpotify;
import com.APIrest.app.services.SpotifyService;
import com.APIrest.app.services.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class WeatherController {


    private RestTemplate restTemplate;
    private WeatherService weatherService;

    private SpotifyService spotifyService;
    private AutenticadorSpotify autenticadorSpotify;

    public WeatherController(RestTemplate restTemplate, WeatherService weatherService, SpotifyService spotifyService, AutenticadorSpotify autenticadorSpotify) {
        this.restTemplate = restTemplate;
        this.weatherService = weatherService;
        this.spotifyService = spotifyService;
        this.autenticadorSpotify = autenticadorSpotify;
    }
    @GetMapping(value="API", params={"city"})
    public Object getWeatherByName(@RequestParam("city") String city) throws JsonProcessingException {
        double temp = this.weatherService.findByName(restTemplate,city);
        String genero = tempToGender(temp);
        String token = this.autenticadorSpotify.getAccessToken().getAccessToken();
        return this.spotifyService.getTracks(genero,token);
    }
    @GetMapping(value="API", params={"lat","lon"})
    public Object getWeatherByCoordinates(@RequestParam("lat")String latitud,
                                          @RequestParam("lon")String longitud) throws JsonProcessingException {
        Object temp = this.weatherService.findByCoordinates(restTemplate,latitud,longitud);
        return temp;
    }
    private String tempToGender (double temp){
        if (temp<10.0){return "classical";}
        if (temp<15.0){return "rock";}
        if (temp<30.0){return "pop";}
        return "party";
    }
    @GetMapping("token")

    public Token verToken(){
        Token seeToken = this.autenticadorSpotify.getAccessToken();
        return seeToken;
    }
}
