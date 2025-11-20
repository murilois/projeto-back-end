package com.stockcontrol.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "categorias")
public class Categoria implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(nullable = false, length = 100)
	private String nome;
	
	private String descricao;
	
	private Boolean ativo = true;
	
	@Column(updatable = false)
	private LocalDateTime criadoEm;
	//Pra quebrar a saida do json de todos
	@JsonIgnore
	@OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<Produto> produtos = new ArrayList<>();
	
	@PrePersist
	public void prePersist() {
		this.criadoEm = LocalDateTime.now();
	}
	
}
