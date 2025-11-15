package com.stockcontrol.movimentacao_service.web.rest;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockcontrol.movimentacao_service.dto.MovimentacaoRequestDTO;
import com.stockcontrol.movimentacao_service.dto.MovimentacaoResponseDTO;
import com.stockcontrol.movimentacao_service.service.MovimentacaoService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/v1/movimentacoes")
@RequiredArgsConstructor
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @PostMapping
    public ResponseEntity<MovimentacaoResponseDTO> criar(@Valid @RequestBody MovimentacaoRequestDTO dto) {
        return ResponseEntity.ok(movimentacaoService.criarMovimentacao(dto));
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarPorProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(movimentacaoService.listarPorProduto(produtoId));
    }
}
