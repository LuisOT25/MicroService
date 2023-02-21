package com.APIrest.app.entitys;

import jakarta.persistence.*;

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

    public Consulta() {
    }

    public Consulta(String cityName, double temp, String description, LocalDateTime fecha) {
        this.cityName = cityName;
        this.temp = temp;
        this.descripcion = description;
        this.fecha = fecha;
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
}
