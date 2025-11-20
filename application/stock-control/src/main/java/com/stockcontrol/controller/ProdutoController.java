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

import com.stockcontrol.dto.ProdutoDTO;
import com.stockcontrol.model.Produto;
import com.stockcontrol.service.ProdutoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos") 
@Tag(name = "Produtos", description = "Gerenciamento de catálogo de produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Operation(summary = "Cadastrar produto", description = "Cria um novo produto vinculado a uma categoria existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro de validação ou Categoria inexistente")
    })
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid ProdutoDTO dto) {
        try {
            Produto saved = service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos cadastrados")
    @GetMapping
    public ResponseEntity<List<Produto>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Buscar produto", description = "Busca um produto específico pelo UUID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable UUID id) {
        Optional<Produto> produtoOptional = service.findById(id);
        if (produtoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        return ResponseEntity.ok(produtoOptional.get());
    }

    @Operation(summary = "Excluir produto", description = "Remove um produto do catálogo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        Optional<Produto> produtoOptional = service.findById(id);
        if (produtoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}