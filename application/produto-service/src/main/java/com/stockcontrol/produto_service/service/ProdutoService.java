package com.stockcontrol.produto_service.service;

import java.math.BigDecimal;
import java.util.List;

import com.stockcontrol.produto_service.dto.EstoqueDTO;
import com.stockcontrol.produto_service.dto.ProdutoRequestDTO;
import com.stockcontrol.produto_service.dto.ProdutoResponseDTO;

public interface ProdutoService {

    ProdutoResponseDTO criarProduto(ProdutoRequestDTO dto);

    ProdutoResponseDTO buscarPorId(Long id);

    List<ProdutoResponseDTO> listarTodos();

    ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO dto);

    void deletarProduto(Long id);

    EstoqueDTO atualizarEstoque(Long produtoId, BigDecimal deltaQuantidade);
}
