package com.stockcontrol.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import com.stockcontrol.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID>{

}
