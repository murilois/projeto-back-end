package com.stockcontrol.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.UUID;

@Data
@Schema(description = "Dados de cadastro de Fornecedor")
public class FornecedorDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;

    private String telefone;
    private String email;
    private String endereco;
    private Boolean ativo;
}