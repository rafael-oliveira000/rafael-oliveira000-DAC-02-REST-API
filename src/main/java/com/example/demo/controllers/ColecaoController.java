package com.example.demo.controllers;

import com.example.demo.model.Colecao;
import com.example.demo.model.Colecao;
import com.example.demo.model.Recurso;
import com.example.demo.repository.IColecaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/colecao")
public class ColecaoController {

    @Autowired
    private IColecaoRepository colecaoRepository;

    @GetMapping
    public ResponseEntity<List<Colecao>> getAllTodos() {
        List<Colecao> colecoes = colecaoRepository.findAll();
        return new ResponseEntity<>(colecoes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colecao> getColecaoById(@PathVariable int id) {
        Optional<Colecao> colecao = colecaoRepository.findById(id);
        if (!colecao.isEmpty()) {
            return new ResponseEntity<>(colecao.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Colecao> deleteColecaoById(@PathVariable int id) {
        Optional<Colecao> colecao = colecaoRepository.findById(id);
        if (!colecao.isEmpty()) {
            colecaoRepository.deleteById(id);
            return new ResponseEntity<>(colecao.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colecao> updateColecaoById(@PathVariable int id, @RequestBody Colecao incomingColecao) {
        Optional<Colecao> colecao = colecaoRepository.findById(id);
        if (!colecao.isEmpty()) {
            if (!Objects.equals(incomingColecao.getId(), colecao.get().getId()))
                return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);

            colecaoRepository.save(incomingColecao);
            return new ResponseEntity<>(incomingColecao, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public Colecao create(@RequestBody Colecao colecao) {
        return colecaoRepository.save(colecao);
    }
}
