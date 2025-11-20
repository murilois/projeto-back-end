package com.stockcontrol.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "estoque")
public class Estoque implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "id", nullable = false, unique = true)
    private Produto produto;

    @Column(nullable = false)
    private BigDecimal quantidadeAtual = BigDecimal.ZERO;

    private BigDecimal quantidadeMinima = BigDecimal.ZERO;

    private BigDecimal quantidadeMaxima;

    private String localizacao;

    private LocalDateTime atualizadoEm;

    @PrePersist
    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }

    public void adicionar(BigDecimal qtd) {
        if (qtd.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Quantidade deve ser positiva");
        this.quantidadeAtual = this.quantidadeAtual.add(qtd);
    }

    public void remover(BigDecimal qtd) {
        if (qtd.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Quantidade deve ser positiva");
        if (!temEstoqueSuficiente(qtd)) throw new IllegalArgumentException("Saldo insuficiente no estoque");
        this.quantidadeAtual = this.quantidadeAtual.subtract(qtd);
    }

    public boolean temEstoqueSuficiente(BigDecimal qtd) {
        return this.quantidadeAtual.compareTo(qtd) >= 0;
    }
}