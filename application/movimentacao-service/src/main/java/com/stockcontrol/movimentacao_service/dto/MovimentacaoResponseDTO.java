package com.stockcontrol.movimentacao_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.stockcontrol.movimentacao_service.domain.TipoMovimentacao;
import com.stockcontrol.movimentacao_service.client.ProdutoDTO;
import com.stockcontrol.movimentacao_service.client.UsuarioDTO;

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
public class MovimentacaoResponseDTO {

    private Long id;
    private TipoMovimentacao tipoMovimentacao;
    private BigDecimal quantidade;
    private String motivo;
    private LocalDateTime dataMovimentacao;
    private ProdutoDTO produto;
    private UsuarioDTO usuario;
    private BigDecimal quantidadeAnterior;
    private BigDecimal quantidadeAtual;
}
