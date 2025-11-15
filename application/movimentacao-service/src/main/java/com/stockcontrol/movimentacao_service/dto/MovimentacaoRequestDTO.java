package com.stockcontrol.movimentacao_service.dto;

import java.math.BigDecimal;

import com.stockcontrol.movimentacao_service.domain.TipoMovimentacao;

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
public class MovimentacaoRequestDTO {

    @NotNull
    private Long produtoId;

    @NotNull
    private Long usuarioId;

    @NotNull
    private TipoMovimentacao tipoMovimentacao;

    @NotNull
    private BigDecimal quantidade;

    private String motivo;
}
