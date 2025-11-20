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

import com.stockcontrol.dto.CategoriaDTO;
import com.stockcontrol.model.Categoria;
import com.stockcontrol.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Gerenciamento de categorias de produtos")
public class CategoriaController {

    @Autowired
    private CategoriaService service;
    
    @Operation(summary = "Criar nova categoria", description = "Cadastra uma nova categoria no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Categoria> create(@RequestBody @Valid CategoriaDTO dto) {
        Categoria saved = service.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @Operation(summary = "Listar todas", description = "Retorna a lista completa de categorias")
    @ApiResponse(responseCode = "200", description = "Operação bem sucedida")
    @GetMapping
    public ResponseEntity<List<Categoria>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @Operation(summary = "Buscar por ID", description = "Retorna uma única categoria baseada no UUID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable UUID id) {
        Optional<Categoria> categoriaOptional = service.findById(id);
        if (categoriaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");
        }
        return ResponseEntity.ok(categoriaOptional.get());
    }
    
    @Operation(summary = "Deletar categoria", description = "Remove uma categoria do sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"), // 204 é 'No Content', padrão para delete
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        Optional<Categoria> categoriaOptional = service.findById(id);
        if (categoriaOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não encontrada.");
        }
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Categoria deletada com sucesso.");
    }
}