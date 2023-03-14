package com.APIrest.app.services;

import com.APIrest.app.repositorys.RepoUsuarios;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuariosService {

    RepoUsuarios repoUsuarios;

    public UsuariosService(RepoUsuarios repoUsuarios) {
        this.repoUsuarios = repoUsuarios;
    }

    public String getJWTToken(String username) {
        String secretKey = "mySecretKey568764432%jdbjhshjbjkja647483898278yufgjdijwhuidwuq8y2738478689138dqwgubhjst72782eugdw7y27827823udefwhi3uudwhjkdwiuhef8y73278";
        AuthorityUtils AuthorityUtils = null;
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
        String token = Jwts
                .builder()
                .setId("JWTAZ")
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

    public boolean analizarToken(String token){
        if (!this.repoUsuarios.existsByToken(token)){throw new RuntimeException("\"error\":Token invalido");}
        if (this.repoUsuarios.existsByTokenAndExpiracion(token, LocalDateTime.now())){
            return true;
        }
       return false;
    }

}
