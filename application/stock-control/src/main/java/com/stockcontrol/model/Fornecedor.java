package com.stockcontrol.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "fornecedores")
public class Fornecedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 20)
    private String cnpj;

    private String telefone;
    private String email;
    private String endereco;
    private Boolean ativo = true;

    @Column(updatable = false)
    private LocalDateTime criadoEm;

    @JsonIgnore 
    @OneToMany(mappedBy = "fornecedor", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Produto> produtos = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }
}