package com.APIrest.app.entitys;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {

    public Usuario(){
    }
    @Id
    private String usuario;
    @Column(name = "contraseña")
    private String password;
    @Column(name = "token")
    private String token;
    @Column(name = "expiracion")
    private LocalDateTime expiracion;

    public Usuario(String usuario, String password, String token, LocalDateTime expiracion) {
        this.usuario = usuario;
        this.password = password;
        this.token = token;
        this.expiracion = expiracion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiracion() {
        return expiracion;
    }

    public void setExpiracion(LocalDateTime expiracion) {
        this.expiracion = expiracion;
    }
}
