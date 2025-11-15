package com.stockcontrol.produto_service.dto;

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
public class FornecedorResponseDTO {

    private Long id;
    private String nome;
    private String cnpj;
    private String telefone;
    private String email;
    private Boolean ativo;

}
