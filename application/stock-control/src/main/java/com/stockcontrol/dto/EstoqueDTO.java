package com.stockcontrol.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "Dados de leitura do Estoque")
public class EstoqueDTO {

    private UUID id;
    
    @Schema(description = "ID do produto vinculado")
    private UUID produtoId;
    
    @Schema(description = "Nome do produto vinculado")
    private String produtoNome;

    private BigDecimal quantidadeAtual;
    private BigDecimal quantidadeMinima;
    private BigDecimal quantidadeMaxima;
    private String localizacao;
    private LocalDateTime atualizadoEm;
}