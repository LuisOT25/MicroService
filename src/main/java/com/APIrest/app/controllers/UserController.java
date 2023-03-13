package com.APIrest.app.controllers;

import com.APIrest.app.entitys.Usuario;
import com.APIrest.app.repositorys.RepoUsuarios;
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

    public UserController(RepoUsuarios repoUsuarios) {
        this.repoUsuarios = repoUsuarios;
    }

    @PostMapping(value ="api/login", params={"user","password"})
    public ResponseEntity<Object> login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
        if (this.repoUsuarios.existsById(username)){
            return ResponseEntity.badRequest().body("Este usuario ya existe");
        }
        String token = getJWTToken(username);
        Usuario user = new Usuario(username,pwd,token, LocalDateTime.now().plusHours(1));
        this.repoUsuarios.save(user);
        return ResponseEntity.ok(user);
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey568764432%jdbjhshjbjkja647483898278yufgjdijwhuidwuq8y2738478689138dqwgubhjst72782eugdw7y27827823udefwhi3uudwhjkdwiuhef8y73278";
        AuthorityUtils AuthorityUtils = null;
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
        return token;
    }
    @Transactional
    @PutMapping(value = "api/token", params = {"user", "password"})
    public ResponseEntity<Object> actualizar(@RequestParam("user") String user, @RequestParam("password") String pwd){
        if (this.repoUsuarios.existsById(user)){
            String token = getJWTToken(user);
            this.repoUsuarios.cambiarToken(token,user,pwd, LocalDateTime.now().plusHours(1));
            return ResponseEntity.ok(this.repoUsuarios.findById(user));
        }else {
            return ResponseEntity.badRequest().body("Este usuario no existe");
        }
    }

}
