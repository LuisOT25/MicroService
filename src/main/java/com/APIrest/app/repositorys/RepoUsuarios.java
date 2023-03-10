package com.APIrest.app.repositorys;

import com.APIrest.app.entitys.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoUsuarios extends JpaRepository<Usuario,String> {
}
