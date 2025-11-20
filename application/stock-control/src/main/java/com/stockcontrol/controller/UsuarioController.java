package com.stockcontrol.controller;

import com.stockcontrol.dto.UsuarioDTO;
import com.stockcontrol.model.Usuario;
import com.stockcontrol.service.UsuarioService;
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
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Gestão de usuários e acesso")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Operation(summary = "Criar usuário", description = "Cadastra um novo operador ou administrador")
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid UsuarioDTO dto) {
        try {
            Usuario saved = service.save(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Listar usuários", description = "Lista todos os usuários cadastrados")
    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Buscar por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable UUID id) {
        Optional<Usuario> usuarioOptional = service.findById(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
        return ResponseEntity.ok(usuarioOptional.get());
    }
    
    @Operation(summary = "Deletar usuário")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable UUID id) {
        Optional<Usuario> usuarioOptional = service.findById(id);
        if (usuarioOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}