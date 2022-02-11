package com.example.demo.repository;

import com.example.demo.model.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRecursoRepository extends JpaRepository<Recurso, Integer> {

    @Query(value = "select * from recursos inner join autores_recursos ar on recursos.id = ar.recurso_id where autor_id = ?1", nativeQuery = true)
    List<Recurso> findRecursosByAutorId(int autor_id);

    @Query(value = "select * from recursos where colecao_id = ?1", nativeQuery = true)
    List<Recurso> findRecursosByColecaoId(Integer colecaoId);
}
