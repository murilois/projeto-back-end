package com.stockcontrol.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stockcontrol.model.enums.PerfilUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true) // Unique = True, o email não pode repetir, portanto ele é único
    private String email;
    
    @JsonIgnore
    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING) // EnumType.String salva "ADMIN" no banco, não números do enum tipo 0, 1
    @Column(nullable = false)
    private PerfilUsuario perfil;

    private Boolean ativo = true;

    @Column(updatable = false)
    private LocalDateTime criadoEm;

    private LocalDateTime ultimoLogin;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }
}