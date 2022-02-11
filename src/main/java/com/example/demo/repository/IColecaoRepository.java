package com.example.demo.repository;

import com.example.demo.model.Colecao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IColecaoRepository extends JpaRepository<Colecao, Integer> {}
