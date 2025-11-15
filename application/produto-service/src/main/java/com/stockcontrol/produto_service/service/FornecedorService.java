package com.stockcontrol.produto_service.service;

import java.util.List;

import com.stockcontrol.produto_service.dto.FornecedorRequestDTO;
import com.stockcontrol.produto_service.dto.FornecedorResponseDTO;

public interface FornecedorService {

    FornecedorResponseDTO criarFornecedor(FornecedorRequestDTO dto);

    FornecedorResponseDTO buscarPorId(Long id);

    List<FornecedorResponseDTO> listarTodos();

    FornecedorResponseDTO atualizarFornecedor(Long id, FornecedorRequestDTO dto);

    void deletarFornecedor(Long id);
}
