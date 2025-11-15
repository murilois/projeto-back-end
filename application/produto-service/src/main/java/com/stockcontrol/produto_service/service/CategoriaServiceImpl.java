package com.stockcontrol.produto_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockcontrol.produto_service.domain.Categoria;
import com.stockcontrol.produto_service.dto.CategoriaRequestDTO;
import com.stockcontrol.produto_service.dto.CategoriaResponseDTO;
import com.stockcontrol.produto_service.exception.CategoriaNotFoundException;
import com.stockcontrol.produto_service.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public CategoriaResponseDTO criarCategoria(CategoriaRequestDTO dto) {
        Categoria categoria = Categoria.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .build();
        Categoria salvo = categoriaRepository.save(categoria);
        return mapToDto(salvo);
    }

    @Override
    public CategoriaResponseDTO buscarPorId(Long id) {
        return categoriaRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
    }

    @Override
    public List<CategoriaResponseDTO> listarTodas() {
        return categoriaRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoriaResponseDTO atualizarCategoria(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
        categoria.setNome(dto.getNome());
        categoria.setDescricao(dto.getDescricao());
        Categoria atualizado = categoriaRepository.save(categoria);
        return mapToDto(atualizado);
    }

    @Override
    @Transactional
    public void deletarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
        categoriaRepository.delete(categoria);
    }

    private CategoriaResponseDTO mapToDto(Categoria categoria) {
        return CategoriaResponseDTO.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .descricao(categoria.getDescricao())
                .ativo(categoria.getAtivo())
                .build();
    }
}
