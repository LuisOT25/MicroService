package com.APIrest.app.dtos;

public class Tracks {
    String nombre;
    String artista;

    public Tracks(String nombre, String artista) {
        this.nombre = nombre;
        this.artista = artista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    @Override
    public String toString() {
        return "{\"nombre\":" + nombre +", \"artista\":" + artista +" }";
    }
}
