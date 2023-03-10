package com.APIrest.app.controllers;

import com.APIrest.app.entitys.Usuario;
import com.APIrest.app.repositorys.RepoUsuarios;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
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
    public Usuario login(@RequestParam("user") String username, @RequestParam("password") String pwd) {

        String token = getJWTToken(username);
        Usuario user = new Usuario(username,pwd,token, LocalDateTime.now().plusHours(1));
        this.repoUsuarios.save(user);
        return user;
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey568764432%jdbjhshjbjkja647483898278yufgjdijwhuidwuq8y2738478689138dqwgubhjst72782eugdw7y27827823udwhjkdwiuhef8y73278";
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
                .setExpiration(new Date(System.currentTimeMillis() + 360000))
                .signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

        return token;
    }

}
