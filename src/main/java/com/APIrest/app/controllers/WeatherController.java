package com.APIrest.app.controllers;

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

    @Autowired
    public WeatherController(RestTemplate restTemplate, WeatherService weatherService) {
        this.restTemplate = restTemplate;
        this.weatherService = weatherService;
    }


    @GetMapping(value="test", params={"city"})
    public Object getWeatherByName(@RequestParam("city") String city) throws JsonProcessingException {
        Object temp = this.weatherService.findByName(restTemplate,city);
        return temp;
    }
    @GetMapping(value="test", params={"lat","lon"})
    public Object getWeatherByCoordinates(@RequestParam("lat")String latitud,
                                          @RequestParam("lon")String longitud) throws JsonProcessingException {
        Object temp = this.weatherService.findByCoordinates(restTemplate,latitud,longitud);
        return temp;
    }

}
