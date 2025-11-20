package com.stockcontrol.service;

import com.stockcontrol.dto.UsuarioDTO;
import com.stockcontrol.model.Usuario;
import com.stockcontrol.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario save(UsuarioDTO dto) {
        if (dto.getId() == null && repository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado no sistema.");
        }

        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(dto, usuario);
        // Caso escale, podemos adicionar aqui a criptografia de senha
        return repository.save(usuario);
    }

    public List<Usuario> findAll() {
        return repository.findAll();
    }

    public Optional<Usuario> findById(UUID id) {
        return repository.findById(id);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}