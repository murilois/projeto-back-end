package com.stockcontrol.repository;

import com.stockcontrol.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, UUID> {
	// Opcional pra buscar o produto pelo ID
    Optional<Estoque> findByProdutoId(UUID produtoId);
}