package com.stockcontrol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stockcontrol.model.enums.PerfilUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
@Schema(description = "Dados de cadastro de usuário")
public class UsuarioDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // A senha não sai no JSON, só entra, por isso o WRITE_ONLY
    private String senha;

    @NotNull(message = "Perfil é obrigatório (ADMIN ou OPERADOR)")
    private PerfilUsuario perfil;

    private Boolean ativo;
}