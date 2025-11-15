package com.stockcontrol.produto_service.exception;

public class CategoriaNotFoundException extends RuntimeException {

    public CategoriaNotFoundException(Long id) {
        super("Categoria não encontrada: " + id);
    }
}
