package com.stockcontrol.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockcontrol.dto.MovimentacaoDTO;
import com.stockcontrol.service.MovimentacaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/movimentacoes")
@Tag(name = "Movimentações", description = "Registro de entradas e saídas de estoque")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService service;

    @Operation(summary = "Registrar movimentação", description = "Registra entrada ou saída e atualiza o saldo do estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movimentação registrada e saldo atualizado"),
            @ApiResponse(responseCode = "400", description = "Erro de Negócio (Saldo insuficiente, Produto não encontrado, Permissão negada)")
        })
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid MovimentacaoDTO dto) {
    	//Esse try catch aq é opcional pq implementamos o GlobalExceptionHandler, mas mantivemos para evitar erros seguindo a lógica anterior.
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarMovimentacao(dto));
        } catch (IllegalArgumentException e) {
            // Saldo insuficiente ou IDs inválidos cai aqui
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}