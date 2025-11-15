package com.stockcontrol.usuario_service.service;

import java.util.List;

import com.stockcontrol.usuario_service.dto.UsuarioRequestDTO;
import com.stockcontrol.usuario_service.dto.UsuarioResponseDTO;

public interface UsuarioService {
    UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto);
    UsuarioResponseDTO buscarUsuarioPorId(Long id);
    List<UsuarioResponseDTO> listarUsuarios();
    UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto);
    void deletarUsuario(Long id);
    boolean validarSenha(Long id, String senha);
}
