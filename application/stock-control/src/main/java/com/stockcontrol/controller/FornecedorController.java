package com.stockcontrol.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockcontrol.dto.FornecedorDTO;
import com.stockcontrol.model.Fornecedor;
import com.stockcontrol.service.FornecedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/fornecedores")
@Tag(name = "Fornecedores", description = "Gestão de parceiros e fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorService service;

    @Operation(summary = "Cadastrar fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fornecedor foi cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "CNPJ duplicado ou dados inválidos")
        })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor foi encontrado"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não foi encontrado")
        })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable UUID id) {
        Optional<Fornecedor> opt = service.findById(id);
        return opt.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não foi encontrado."));
    }

    @Operation(summary = "Deletar fornecedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Fornecedor foi deletado"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não foi encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro de integridade (Fornecedor ainda possui produtos vinculados)")
        })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        Optional<Fornecedor> opt = service.findById(id);
        if (opt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não foi encontrado.");
        
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}