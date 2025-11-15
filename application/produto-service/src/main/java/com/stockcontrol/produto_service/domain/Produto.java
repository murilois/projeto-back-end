package com.stockcontrol.produto_service.domain;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String codigoBarras;

    @Column(nullable = false)
    private BigDecimal precoCompra;

    @Column(nullable = false)
    private BigDecimal precoVenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "estoque_id")
    private Estoque estoque;

    @PrePersist
    public void garantirEstoque() {
        if (estoque == null) {
            adicionarEstoqueInicial(null, null, null);
        }
    }

    public void adicionarEstoqueInicial(BigDecimal quantidadeAtual, BigDecimal quantidadeMinima, String localizacao) {
        if (estoque == null) {
            estoque = new Estoque();
        }
        estoque.setQuantidadeAtual(quantidadeAtual != null ? quantidadeAtual : BigDecimal.ZERO);
        estoque.setQuantidadeMinima(quantidadeMinima != null ? quantidadeMinima : BigDecimal.ZERO);
        estoque.setLocalizacao(localizacao);
        estoque.setProduto(this);
    }
}
