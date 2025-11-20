package com.stockcontrol.controller;

import com.stockcontrol.dto.FornecedorDTO;
import com.stockcontrol.model.Fornecedor;
import com.stockcontrol.service.FornecedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/fornecedores")
@Tag(name = "Fornecedores", description = "Gestão de parceiros e fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService service;

    @Operation(summary = "Cadastrar fornecedor")
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid FornecedorDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Listar fornecedores")
    @GetMapping
    public ResponseEntity<List<Fornecedor>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Buscar por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable UUID id) {
        Optional<Fornecedor> opt = service.findById(id);
        return opt.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado."));
    }

    @Operation(summary = "Deletar fornecedor")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        Optional<Fornecedor> opt = service.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado.");
        
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}