package com.stockcontrol.service;

import com.stockcontrol.dto.CategoriaDTO;
import com.stockcontrol.model.Categoria;
import com.stockcontrol.repository.CategoriaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria save(CategoriaDTO dto) {
        
    	Categoria categoria = new Categoria();
        
        BeanUtils.copyProperties(dto, categoria);
        return repository.save(categoria);
    }

    public List<Categoria> findAll() {
        return repository.findAll();
    }

    public Optional<Categoria> findById(UUID id) {
        return repository.findById(id);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}