package com.example.demo.repository;

import com.example.demo.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IEventoRepository extends JpaRepository<Evento, Integer> {
    @Query(value = "select * from eventos ev inner join colecoes c on c.id = ev.id where ev.data_de_inicio >= ?1 and ev.data_de_inicio < ?2", nativeQuery = true)
    List<Evento> findAllBetweenDates(LocalDate startDate, LocalDate endDate);
}
