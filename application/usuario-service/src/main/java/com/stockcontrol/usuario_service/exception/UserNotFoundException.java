package com.stockcontrol.usuario_service.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Usuário não encontrado: " + id);
    }
}
