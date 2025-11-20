package com.stockcontrol.config;

import com.stockcontrol.dto.ApiErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.stockcontrol.controller")
public class GlobalExceptionHandler {

    // Primeiro, aqui ele trata erros de validação do DTO (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        // Pega todas as mensagens de erro dos campos, tipo nome é obrigatório 
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ApiErrorDTO errorDTO = new ApiErrorDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Validação",
                errors,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    // Aqui trata erros de Regra de Negócio (IllegalArgumentException)
    // Ex: Saldo insuficiente, CNPJ duplicado, não encontrou o ID lançado no Service
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDTO> handleBusinessException(IllegalArgumentException ex, HttpServletRequest request) {
        ApiErrorDTO errorDTO = new ApiErrorDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de Regra de Negócio",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    // Aqui ele trata qualquer outro erro não previsto (Fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleGenericException(Exception ex, HttpServletRequest request) {
        ApiErrorDTO errorDTO = new ApiErrorDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro Interno do Servidor",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }
}