package com.stockcontrol.produto_service.exception;

public class FornecedorNotFoundException extends RuntimeException {

    public FornecedorNotFoundException(Long id) {
        super("Fornecedor não encontrado: " + id);
    }
}
