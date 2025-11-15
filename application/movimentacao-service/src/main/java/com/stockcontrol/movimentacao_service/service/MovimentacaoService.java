package com.stockcontrol.movimentacao_service.service;

import java.util.List;

import com.stockcontrol.movimentacao_service.dto.MovimentacaoRequestDTO;
import com.stockcontrol.movimentacao_service.dto.MovimentacaoResponseDTO;

public interface MovimentacaoService {

    MovimentacaoResponseDTO criarMovimentacao(MovimentacaoRequestDTO dto);

    List<MovimentacaoResponseDTO> listarPorProduto(Long produtoId);
}
