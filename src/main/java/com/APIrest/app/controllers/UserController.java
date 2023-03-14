package com.APIrest.app.controllers;

import com.APIrest.app.entitys.Usuario;
import com.APIrest.app.repositorys.RepoUsuarios;
import com.APIrest.app.services.UsuariosService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    RepoUsuarios repoUsuarios;

    UsuariosService usuariosService;

    public UserController(RepoUsuarios repoUsuarios, UsuariosService usuariosService) {
        this.repoUsuarios = repoUsuarios;
        this.usuariosService = usuariosService;
    }

    @PostMapping(value ="api/login", params={"user","password"})
    public ResponseEntity<Object> login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
        if (this.repoUsuarios.existsById(username)){
            return ResponseEntity.badRequest().body("¡Este usuario ya existe!");
        }
        String token = this.usuariosService.getJWTToken(username);
        Usuario user = new Usuario(username,pwd,token, LocalDateTime.now().plusHours(1));
        this.repoUsuarios.save(user);
        return ResponseEntity.ok(user);
    }

    @Transactional
    @PutMapping(value = "api/token", params = {"user", "password"})
    public ResponseEntity<Object> actualizar(@RequestParam("user") String user, @RequestParam("password") String pwd){
        if (this.repoUsuarios.existsById(user)){
            String token = this.usuariosService.getJWTToken(user);
            this.repoUsuarios.cambiarToken(token,user,pwd, LocalDateTime.now().plusHours(1));
            return ResponseEntity.ok(this.repoUsuarios.findById(user));
        }else {
            return ResponseEntity.badRequest().body("¡Este usuario no existe!");
        }
    }
}
