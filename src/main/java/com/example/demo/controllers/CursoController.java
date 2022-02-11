package com.example.demo.controllers;

import com.example.demo.helpers.ValidationError;
import com.example.demo.model.Curso;
import com.example.demo.repository.ICursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/curso")
public class CursoController {

    @Autowired
    private ICursoRepository cursoRepository;

    @GetMapping
    public ResponseEntity<List<Curso>> getAllTodos() {
        List<Curso> cursos = cursoRepository.findAll();
        return new ResponseEntity<>(cursos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getCursoById(@PathVariable int id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        if (!curso.isEmpty()) {
            return new ResponseEntity<>(curso.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Curso> deleteCursoById(@PathVariable int id) {
        Optional<Curso> curso = cursoRepository.findById(id);
        if (!curso.isEmpty()) {
            cursoRepository.deleteById(id);
            return new ResponseEntity<>(curso.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> updateCursoById(@PathVariable int id, @RequestBody Curso incomingCurso) {
        Optional<Curso> curso = cursoRepository.findById(id);
        if (!curso.isEmpty()) {
            ValidationError validationError = getValidationErrorByCurso(incomingCurso, true);

            if (validationError.hasError())
                return new ResponseEntity<>(null, validationError.getStatus());

            if (!Objects.equals(incomingCurso.getId(), curso.get().getId()))
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);

            cursoRepository.save(incomingCurso);
            return new ResponseEntity<>(incomingCurso, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public Curso create(@RequestBody Curso curso) {
        return cursoRepository.save(curso);
    }

    private ValidationError getValidationErrorByCurso(Curso curso, Boolean strict) {
        if (strict && curso.getId() == null) {
            return new ValidationError(HttpStatus.BAD_REQUEST, "Strict cursos must have a 'ID'");
        } else if (curso.getDataDeRegistro() == null) {
            return new ValidationError(HttpStatus.BAD_REQUEST, "cursos must have a 'data_de_registro'");
        }

        return new ValidationError();
    }
}
