package com.APIrest.app.entitys;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column(name = "ciudad")
    public String cityName;
    @Column(name = "temperatura")
    public double temp;
    @Column(name = "descripcion")
    public String descripcion;
    @Column(name = "fecha")
    public LocalDateTime fecha;
    @Column(name = "recomendacion")
    public String genero;
    @Column(name = "latitud")
    public double lat;
    @Column(name = "longitud")
    public double lon;


    public Consulta() {
    }

    public Consulta(String cityName, double temp, String description, LocalDateTime fecha, String genero, double lat, double lon) {
        this.cityName = cityName;
        this.temp = temp;
        this.descripcion = description;
        this.fecha = fecha;
        this.genero= genero;
        this.lat=lat;
        this.lon=lon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getGenero() {return genero;}

    public void setGenero(String genero) {this.genero = genero;}

}
