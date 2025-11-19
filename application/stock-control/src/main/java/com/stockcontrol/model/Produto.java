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
@Table(name = "produtos")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(unique = true, length = 50)
    private String codigoBarras;

    @Column(precision = 19, scale = 2)
    private BigDecimal precoCompra;

    @Column(precision = 19, scale = 2)
    private BigDecimal precoVenda;

    private String unidadeMedida;

    private Boolean ativo = true;

    @Column(updatable = false)
    private LocalDateTime criadoEm;
    
    private LocalDateTime atualizadoEm;


    @ManyToOne(optional = false) 
    @JoinColumn(name = "categoria_id") 
    private Categoria categoria;

    
    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }
}