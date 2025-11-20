package com.stockcontrol.controller;

import com.stockcontrol.dto.MovimentacaoDTO;
import com.stockcontrol.service.MovimentacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movimentacoes")
@Tag(name = "Movimentações", description = "Registro de entradas e saídas de estoque")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService service;

    @Operation(summary = "Registrar movimentação", description = "Registra entrada ou saída e atualiza o saldo do estoque")
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid MovimentacaoDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarMovimentacao(dto));
        } catch (IllegalArgumentException e) {
            // Saldo insuficiente ou IDs inválidos cai aqui
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}