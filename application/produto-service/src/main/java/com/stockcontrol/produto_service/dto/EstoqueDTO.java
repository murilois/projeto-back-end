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
public class EstoqueDTO {

    private Long id;
    private BigDecimal quantidadeAtual;
    private BigDecimal quantidadeMinima;
    private String localizacao;

}
