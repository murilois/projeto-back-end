package com.stockcontrol.produto_service.service;

import java.util.List;

import com.stockcontrol.produto_service.dto.CategoriaRequestDTO;
import com.stockcontrol.produto_service.dto.CategoriaResponseDTO;

public interface CategoriaService {

    CategoriaResponseDTO criarCategoria(CategoriaRequestDTO dto);

    CategoriaResponseDTO buscarPorId(Long id);

    List<CategoriaResponseDTO> listarTodas();

    CategoriaResponseDTO atualizarCategoria(Long id, CategoriaRequestDTO dto);

    void deletarCategoria(Long id);
}
