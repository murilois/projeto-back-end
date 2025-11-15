package com.stockcontrol.usuario_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockcontrol.usuario_service.domain.Usuario;
import com.stockcontrol.usuario_service.dto.UsuarioRequestDTO;
import com.stockcontrol.usuario_service.dto.UsuarioResponseDTO;
import com.stockcontrol.usuario_service.exception.UserNotFoundException;
import com.stockcontrol.usuario_service.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto) {
        Usuario usuario = mapToEntity(dto);
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setCriadoEm(LocalDateTime.now());
        Usuario salvo = usuarioRepository.save(usuario);
        return mapToDto(salvo);
    }

    @Override
    public UsuarioResponseDTO buscarUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return mapToDto(usuario);
    }

    @Override
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setPerfil(dto.getPerfil());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        Usuario atualizado = usuarioRepository.save(usuario);
        return mapToDto(atualizado);
    }

    @Override
    @Transactional
    public void deletarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        usuarioRepository.delete(usuario);
    }

    @Override
    public boolean validarSenha(Long id, String senha) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return passwordEncoder.matches(senha, usuario.getSenha());
    }

    private UsuarioResponseDTO mapToDto(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .perfil(usuario.getPerfil())
                .ativo(usuario.getAtivo())
                .ultimoLogin(usuario.getUltimoLogin())
                .build();
    }

    private Usuario mapToEntity(UsuarioRequestDTO dto) {
        return Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .perfil(dto.getPerfil())
                .ativo(Boolean.TRUE)
                .ultimoLogin(LocalDateTime.now())
                .build();
    }
}
