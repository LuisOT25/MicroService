package com.APIrest.app.repositorys;

import com.APIrest.app.entitys.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoConsultas extends JpaRepository<Consulta,Integer> {
}
