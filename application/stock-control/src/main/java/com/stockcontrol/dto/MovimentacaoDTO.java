package com.stockcontrol.dto;

import com.stockcontrol.model.enums.TipoMovimentacao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "Dados para registrar entrada ou saída de estoque")
public class MovimentacaoDTO {

    @NotNull(message = "Produto é obrigatório")
    private UUID produtoId;

    @NotNull(message = "Usuário responsável é obrigatório")
    private UUID usuarioId;

    @NotNull(message = "Tipo de movimentação é obrigatório")
    private TipoMovimentacao tipo;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser maior que zero")
    private BigDecimal quantidade;

    private String motivo;
}