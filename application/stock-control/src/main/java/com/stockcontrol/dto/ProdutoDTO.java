package com.stockcontrol.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "Objeto de transferência para criação e atualização de Produtos")
public class ProdutoDTO {

    @Schema(description = "ID do produto (Leitura)", accessMode = Schema.AccessMode.READ_ONLY)
    private UUID id;

    @Schema(example = "Smartphone Iphone 15", description = "Nome comercial do produto")
    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @Schema(example = "7891234567890", description = "Código EAN/GTIN único")
    @NotBlank(message = "O código de barras é obrigatório")
    private String codigoBarras;

    @Schema(example = "2500.00", description = "Preço de aquisição")
    @NotNull(message = "Preço de compra é obrigatório")
    @Positive(message = "O preço de compra deve ser positivo")
    private BigDecimal precoCompra;

    @Schema(example = "3500.00", description = "Preço final de venda")
    @NotNull(message = "Preço de venda é obrigatório")
    @Positive(message = "O preço de venda deve ser positivo")
    private BigDecimal precoVenda;

    @Schema(example = "UN", description = "Unidade de medida (UN, KG, LT)")
    private String unidadeMedida;
    
    @Schema(description = "Status do produto", defaultValue = "true")
    private Boolean ativo;
    
    @Schema(description = "Quantidade mínima para alerta de estoque baixo", example = "10")
    @Positive(message = "Estoque mínimo deve ser positivo")
    private BigDecimal estoqueMinimo;
    
    @Schema(description = "UUID da Categoria a qual este produto pertence", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    @NotNull(message = "A categoria é obrigatória")
    private UUID categoriaId;
    
    @Schema(description = "UUID do Fornecedor", example = "uuid-do-fornecedor-aqui")
    @NotNull(message = "Fornecedor é obrigatório")
    private UUID fornecedorId;
    
    
}    