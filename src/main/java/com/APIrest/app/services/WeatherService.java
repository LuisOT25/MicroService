package com.APIrest.app.services;

import com.APIrest.app.entitys.Consulta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class WeatherService {
    @Value("${weather.api_base_url}")
    String openWeatherBaseUrl;
    @Value("${openWeather.api_key}")
    String apiKey;

    public Consulta findByName(RestTemplate restTemplate, String cityName) throws JsonProcessingException {
        String url = openWeatherBaseUrl+"q="+cityName+"&units=metric&appid="+apiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        double temp = root.path("main").path("temp").doubleValue();
        String descripcion=root.path("weather").path("description").textValue();
        LocalDateTime fecha = LocalDateTime.now();
        return new Consulta(cityName,temp,descripcion,fecha);
    }
    public double findByCoordinates(RestTemplate restTemplate, String lat, String lon) throws JsonProcessingException {
        String url = openWeatherBaseUrl+"lat="+lat+"&lon="+lon+"&units=metric&appid="+apiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode temp = root.path("main").path("temp");
        return temp.doubleValue();
    }
}
