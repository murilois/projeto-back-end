package com.stockcontrol.produto_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stockcontrol.produto_service.domain.Estoque;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
}
