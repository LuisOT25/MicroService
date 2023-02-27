package com.APIrest.app.services;

import com.APIrest.app.entitys.Consulta;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public Consulta findByName(String cityName){
        try{
            RestTemplate restTemplate = new RestTemplate();
            String url = openWeatherBaseUrl + "q=" + cityName + "&units=metric&appid=" + apiKey;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            if (!response.getStatusCode().is2xxSuccessful()) {
                String message = root.path("message").textValue();
                throw new RuntimeException(message);
            }
            double temp = root.path("main").path("temp").doubleValue();
            String descripcion = root.path("weather").elements().next().path("description").textValue();
            LocalDateTime fecha = LocalDateTime.now();
            String genero = tempToGender(temp);
            return new Consulta(cityName, temp, descripcion, fecha, genero);
        }catch (JsonProcessingException ex){
            throw new RuntimeException("Hubo un  error en el proceso");
        }
    }
    public Consulta findByCoordinates(String lat, String lon){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = openWeatherBaseUrl + "lat=" + lat + "&lon=" + lon + "&units=metric&appid=" + apiKey;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            if (!response.getStatusCode().is2xxSuccessful()) {
                String message = root.path("message").textValue();
                throw new RuntimeException(message);
            }
            double temp = root.path("main").path("temp").doubleValue();
            String descripcion = root.path("weather").elements().next().path("description").textValue();
            LocalDateTime fecha = LocalDateTime.now();
            String cityName = root.path("name").textValue();
            String genero = tempToGender(temp);
            return new Consulta(cityName, temp, descripcion, fecha, genero);
        }catch (JsonProcessingException ex){
            throw new RuntimeException("Hubo un error en proceso");
        }
    }
    private String tempToGender (double temp){
        if (temp<10.0){return "classical";}
        if (temp<15.0){return "rock";}
        if (temp<30.0){return "pop";}
        return "party";
    }
}
