package com.stockcontrol.produto_service.dto;

import java.math.BigDecimal;

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
public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private String codigoBarras;
    private BigDecimal precoCompra;
    private BigDecimal precoVenda;
    private CategoriaResponseDTO categoria;
    private FornecedorResponseDTO fornecedor;
    private EstoqueDTO estoque;

}
