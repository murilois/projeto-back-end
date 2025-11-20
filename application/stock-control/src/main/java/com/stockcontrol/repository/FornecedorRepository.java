package com.stockcontrol.repository;

import com.stockcontrol.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, UUID> {
    boolean existsByCnpj(String cnpj);
}