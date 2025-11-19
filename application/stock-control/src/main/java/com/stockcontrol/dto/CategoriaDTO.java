package com.stockcontrol.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Objeto de transferência de dados da Categoria")
public class CategoriaDTO {
	@Schema(description = "Identificador único (UUID)", example = "123e4567-e89b-12d3-a456-426614174000", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;
	
	@Schema(description = "Nome da categoria", example = "Eletrônicos")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
	
	@Schema(description = "Descrição detalhada", example = "Televisores, computadores e periféricos")
    private String descricao;
    
	@Schema(description = "Status da categoria", example = "true", defaultValue = "true")
    private Boolean ativo;
}