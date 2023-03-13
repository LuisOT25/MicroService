package com.APIrest.app.repositorys;

import com.APIrest.app.entitys.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;

public interface RepoUsuarios extends JpaRepository<Usuario,String> {

    @Modifying
    @Query(value = "UPDATE usuarios SET token = :token, expiracion = :expiracion WHERE usuario = :usuario AND contraseña = :password",
            nativeQuery = true, countQuery = "SELECT count(*) FROM usuarios")
    int cambiarToken(@Param("token") String token, @Param("usuario") String usr, @Param("password") String pwd, @Param("expiracion")LocalDateTime exp);

    /*@Modifying
    @Query(value = "UPDATE usuarios SET token = :token, expiracion = :expiracion WHERE usuario = :usuario AND contraseña = :password", nativeQuery = true)
    String cambiarToken(@Param("token") String token, @Param("usuario") String usr, @Param("password") String pwd, @Param("expiracion")LocalDateTime exp);*/
}
