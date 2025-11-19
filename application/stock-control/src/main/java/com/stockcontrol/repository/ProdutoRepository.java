package com.stockcontrol.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockcontrol.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, UUID>{
	boolean existsByCodigoBarras(String codigoBarras);
}
