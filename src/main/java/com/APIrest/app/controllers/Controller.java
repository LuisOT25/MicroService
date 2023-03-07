package com.APIrest.app.controllers;

import com.APIrest.app.dtos.Token;
import com.APIrest.app.dtos.Tracks;
import com.APIrest.app.entitys.Consulta;
import com.APIrest.app.repositorys.RepoConsultas;
import com.APIrest.app.services.AutenticadorSpotify;
import com.APIrest.app.services.SpotifyService;
import com.APIrest.app.services.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;

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

    @Operation(summary = "Request by name or coordinates ")
    @Parameter(name = "city",description = "Nombre de la ciudad a buscar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = { @Content(schema = @Schema(implementation = Tracks.class)) }),
            @ApiResponse(responseCode = "4xx", description = "Algun parametro invalido",
                    content = @Content),
            @ApiResponse(responseCode = "I/O", description = "Error de conexion",
                    content = @Content)})
    @GetMapping(value="API", params={"city"})
    public ResponseEntity<Object> requestByName(@RequestParam(value = "city", required = false) String city){
        try {
            Consulta data = this.weatherService.findByName(city);
            String json = "{" +
                    "\"ciudad\":{\"nombre\":"+data.cityName+",\"temperatura\":"+data.temp+"" +
                    ",\"descripcion\":"+data.descripcion+",\"latitud\":"+data.lat+",\"longitud\":"+data.lon+"}," +
                    "\"canciones\":"+Arrays.toString(new Object[]{getListTracks(data)}) +"}";
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(json);
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

    @Operation(summary = "Request by name or coordinates")
    @Parameter(name = "lat",description = "latitud de la ciudad a buscar")
    @Parameter(name = "lon",description = "longitud de la ciudad a buscar")
    @GetMapping(value="API", params={"lat","lon"})
    public ResponseEntity<Object> requestByCoordinates(@RequestParam(value = "lat", required = false)String latitud,
                                          @RequestParam(value = "lon", required = false)String longitud){
        try{
            Consulta data = this.weatherService.findByCoordinates(latitud, longitud);
            return ResponseEntity.ok(getListTracks(data));
        }catch (RuntimeException runEx){
            return ResponseEntity.badRequest().body(runEx.getMessage());
        }
    }
}

