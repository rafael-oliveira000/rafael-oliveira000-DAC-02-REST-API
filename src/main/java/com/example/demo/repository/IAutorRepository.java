package com.example.demo.repository;
import com.example.demo.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAutorRepository extends JpaRepository<Autor, Integer> {
    @Query(value = "select * from autores where sobrenome = ?1", nativeQuery = true)
    List<Autor> findAllBySobrenome(String sobrenome);
}