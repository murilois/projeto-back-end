package com.stockcontrol.produto_service.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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
public class ProdutoRequestDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String codigoBarras;

    @NotNull
    private BigDecimal precoCompra;

    @NotNull
    private BigDecimal precoVenda;

    @NotNull
    private Long categoriaId;

    @NotNull
    private Long fornecedorId;

}
