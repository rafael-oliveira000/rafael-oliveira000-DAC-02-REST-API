package com.example.demo.controllers;

import com.example.demo.model.Evento;
import com.example.demo.repository.IEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/evento")
public class EventoController {

    @Autowired
    private IEventoRepository eventoRepository;

    @GetMapping
    public ResponseEntity<List<Evento>> getAllTodos(
            @RequestParam (value = "start_date", required = false) String start,
            @RequestParam (value = "end_date", required = false) String end
    ) {

        if(start != null && end != null) {
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);

            List<Evento> eventos = eventoRepository.findAllBetweenDates(startDate, endDate);
            return new ResponseEntity<>(eventos, HttpStatus.OK);
        }

        List<Evento> eventos = eventoRepository.findAll();
        return new ResponseEntity<>(eventos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Evento> deleteEventoById(@PathVariable int id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        if (!evento.isEmpty()) {
            eventoRepository.deleteById(id);
            return new ResponseEntity<>(evento.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> getEventoById(@PathVariable int id) {
        Optional<Evento> evento = eventoRepository.findById(id);
        if (!evento.isEmpty()) {
            return new ResponseEntity<>(evento.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Evento> create(@RequestBody Evento evento) {
        if(this.isEventoInvalid(evento)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            eventoRepository.save(evento);
            return new ResponseEntity<>(evento, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> updateEventoById(@PathVariable int id, @RequestBody Evento incomingEvento) {
        Optional<Evento> evento = eventoRepository.findById(id);
        if (!evento.isEmpty()) {
            if (!Objects.equals(incomingEvento.getId(), evento.get().getId()) || this.isEventoInvalid(incomingEvento))
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
            eventoRepository.save(incomingEvento);
            return new ResponseEntity<>(incomingEvento, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    private boolean isEventoInvalid(Evento evento) {
        return evento.getDataDeFim() == null || evento.getDataDeInicio() == null;
    }
}
