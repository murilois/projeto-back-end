package com.stockcontrol.produto_service.web.rest;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockcontrol.produto_service.dto.EstoqueDTO;
import com.stockcontrol.produto_service.dto.ProdutoRequestDTO;
import com.stockcontrol.produto_service.dto.ProdutoResponseDTO;
import com.stockcontrol.produto_service.service.ProdutoService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criarProduto(@Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO criado = produtoService.criarProduto(dto);
        return ResponseEntity.created(URI.create("/api/v1/produtos/" + criado.getId())).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarProduto(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Long id,
            @Valid @RequestBody ProdutoRequestDTO dto) {
        return ResponseEntity.ok(produtoService.atualizarProduto(id, dto));
    }

    @PostMapping("/{id}/estoque/atualizar")
    public ResponseEntity<EstoqueDTO> atualizarEstoque(@PathVariable Long id, @RequestBody BigDecimal delta) {
        EstoqueDTO estoqueAtualizado = produtoService.atualizarEstoque(id, delta);
        return ResponseEntity.ok(estoqueAtualizado);
    }
}
