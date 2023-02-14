package com.APIrest.app.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    public Double findByName(RestTemplate restTemplate, String cityName,String APIkey) throws JsonProcessingException {
        String url = "http://api.openweathermap.org/data/2.5/weather?q="+cityName+"&units=metric&appid="+APIkey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode temp = root.path("main").path("temp");
        return temp.doubleValue();
    }
    public Double findByCoordinates(RestTemplate restTemplate, String lat, String lon,String APIkey) throws JsonProcessingException {
        String url = "https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&units=metric&appid="+APIkey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode temp = root.path("main").path("temp");
        return temp.doubleValue();
    }
}
