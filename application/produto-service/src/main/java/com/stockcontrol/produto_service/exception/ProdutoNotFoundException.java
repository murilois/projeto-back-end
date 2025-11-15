package com.stockcontrol.produto_service.exception;

public class ProdutoNotFoundException extends RuntimeException {

    public ProdutoNotFoundException(Long id) {
        super("Produto não encontrado: " + id);
    }
}
