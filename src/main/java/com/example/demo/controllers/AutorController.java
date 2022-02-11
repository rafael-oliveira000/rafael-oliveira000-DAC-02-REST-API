package com.example.demo.controllers;

import com.example.demo.model.Autor;
import com.example.demo.model.Recurso;
import com.example.demo.repository.IAutorRepository;
import com.example.demo.repository.IRecursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/autor")
public class AutorController {

    @Autowired
    private IAutorRepository autorRepository;

    @Autowired
    private IRecursoRepository recursoRepository;

    @GetMapping
    public ResponseEntity<List<Autor>> getAllTodos(
            @RequestParam (value = "sobrenome", required = false) String sobrenome) {

        if (sobrenome != null) {
            List<Autor> autores = autorRepository.findAllBySobrenome(sobrenome);
            return new ResponseEntity<>(autores, HttpStatus.OK);
        }

        List<Autor> autores = autorRepository.findAll();
        return new ResponseEntity<>(autores, HttpStatus.OK);
    }

    @PostMapping("/{autorId}/adiciona/recurso/{recursoId}")
    public ResponseEntity<String> relateAutorWithRecurso(
            @PathVariable (value = "recursoId") int recursoId,
            @PathVariable (value = "autorId") int autorId) {

        Optional<Recurso> recurso = recursoRepository.findById(recursoId);
        Optional<Autor> autor = autorRepository.findById(autorId);

        if (recurso.isEmpty() || autor.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        var updatedAutor = autor.get();
        updatedAutor.appendRecurso(recurso.get());

        autorRepository.save(updatedAutor);
        return new ResponseEntity<String>("Success", HttpStatus.CREATED);
    }

    @PostMapping("/recurso/{recursoId}")
    public ResponseEntity<Autor> createAutor(@PathVariable (value = "recursoId") int recursoId,
                                                 @RequestBody Autor autor) {
        Optional<Recurso> recurso = recursoRepository.findById(recursoId);
        if (recurso.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        autor.appendRecurso(recurso.get());
        autorRepository.save(autor);
        return new ResponseEntity<Autor>(autor, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Autor> updateAutorById(@PathVariable int id, @RequestBody Autor incomingAutor) {
        Optional<Autor> autor = autorRepository.findById(id);
        if (!autor.isEmpty()) {
            if (!Objects.equals(incomingAutor.getId(), autor.get().getId()))
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);

            autorRepository.save(incomingAutor);
            return new ResponseEntity<>(incomingAutor, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autor> getAutorById(@PathVariable int id) {
        Optional<Autor> autor = autorRepository.findById(id);
        if (!autor.isEmpty()) {
            return new ResponseEntity<>(autor.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Autor> deleteAutorById(@PathVariable int id) {
        Optional<Autor> autor = autorRepository.findById(id);
        if (!autor.isEmpty()) {
            autorRepository.deleteById(id);
            return new ResponseEntity<>(autor.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
