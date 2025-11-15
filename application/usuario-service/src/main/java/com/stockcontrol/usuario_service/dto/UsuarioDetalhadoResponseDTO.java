package com.stockcontrol.usuario_service.dto;

import java.time.LocalDateTime;

import com.stockcontrol.usuario_service.domain.PerfilUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDetalhadoResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private PerfilUsuario perfil;
    private Boolean ativo;
    private LocalDateTime ultimoLogin;
    private LocalDateTime criadoEm;

}
