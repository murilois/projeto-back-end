package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/estoque")
public class EstoqueController {
    
    @PostMapping("/adicionar")
    public String adicionarEstoque(@RequestBody String dados) {
        return "Estoque adicionado: " + dados;
    }
    
    @PostMapping("/reduzir")
    public String reduzirEstoque(@RequestBody String dados) {
        return "Estoque reduzido: " + dados;
    }
    
    @GetMapping("/baixo")
    public String estoqueBaixo() {
        return "Produtos com estoque baixo: Banana, Uva";
    }
}