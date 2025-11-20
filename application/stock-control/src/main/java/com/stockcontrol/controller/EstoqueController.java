package com.stockcontrol.controller;

import com.stockcontrol.dto.EstoqueDTO;
import com.stockcontrol.service.EstoqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/estoques")
@Tag(name = "Estoque", description = "Consulta de saldo e posições de estoque")
public class EstoqueController {

    @Autowired
    private EstoqueService service;

    @Operation(summary = "Buscar estoque por produto", description = "Retorna o saldo atual de um produto específico")
    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<EstoqueDTO> getByProduto(@PathVariable UUID produtoId) {
        return ResponseEntity.ok(service.buscarPorProdutoId(produtoId));
    }
}