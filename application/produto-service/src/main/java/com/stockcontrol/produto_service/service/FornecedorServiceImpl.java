package com.stockcontrol.produto_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockcontrol.produto_service.domain.Fornecedor;
import com.stockcontrol.produto_service.dto.FornecedorRequestDTO;
import com.stockcontrol.produto_service.dto.FornecedorResponseDTO;
import com.stockcontrol.produto_service.exception.FornecedorNotFoundException;
import com.stockcontrol.produto_service.repository.FornecedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FornecedorServiceImpl implements FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    @Override
    @Transactional
    public FornecedorResponseDTO criarFornecedor(FornecedorRequestDTO dto) {
        Fornecedor fornecedor = Fornecedor.builder()
                .nome(dto.getNome())
                .cnpj(dto.getCnpj())
                .telefone(dto.getTelefone())
                .email(dto.getEmail())
                .build();
        Fornecedor salvo = fornecedorRepository.save(fornecedor);
        return mapToDto(salvo);
    }

    @Override
    public FornecedorResponseDTO buscarPorId(Long id) {
        return fornecedorRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new FornecedorNotFoundException(id));
    }

    @Override
    public List<FornecedorResponseDTO> listarTodos() {
        return fornecedorRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FornecedorResponseDTO atualizarFornecedor(Long id, FornecedorRequestDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new FornecedorNotFoundException(id));
        fornecedor.setNome(dto.getNome());
        fornecedor.setCnpj(dto.getCnpj());
        fornecedor.setTelefone(dto.getTelefone());
        fornecedor.setEmail(dto.getEmail());
        Fornecedor atualizado = fornecedorRepository.save(fornecedor);
        return mapToDto(atualizado);
    }

    @Override
    @Transactional
    public void deletarFornecedor(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new FornecedorNotFoundException(id));
        fornecedorRepository.delete(fornecedor);
    }

    private FornecedorResponseDTO mapToDto(Fornecedor fornecedor) {
        return FornecedorResponseDTO.builder()
                .id(fornecedor.getId())
                .nome(fornecedor.getNome())
                .cnpj(fornecedor.getCnpj())
                .telefone(fornecedor.getTelefone())
                .email(fornecedor.getEmail())
                .ativo(fornecedor.getAtivo())
                .build();
    }
}
