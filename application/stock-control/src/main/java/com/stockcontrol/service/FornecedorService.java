package com.stockcontrol.service;

import com.stockcontrol.dto.FornecedorDTO;
import com.stockcontrol.model.Fornecedor;
import com.stockcontrol.repository.FornecedorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository repository;

    public Fornecedor save(FornecedorDTO dto) {
        if (dto.getId() == null && repository.existsByCnpj(dto.getCnpj())) {
            throw new IllegalArgumentException("CNPJ já cadastrado.");
        }
        Fornecedor fornecedor = new Fornecedor();
        BeanUtils.copyProperties(dto, fornecedor);
        return repository.save(fornecedor);
    }

    public List<Fornecedor> findAll() { return repository.findAll(); }
    public Optional<Fornecedor> findById(UUID id) { return repository.findById(id); }
    public void delete(UUID id) { repository.deleteById(id); }
}