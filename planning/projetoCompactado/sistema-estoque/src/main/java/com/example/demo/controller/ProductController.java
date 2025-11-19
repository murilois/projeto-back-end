package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProductController {
    
    @GetMapping
    public String listarProdutos() {
        return "Lista de produtos: Maçã, Banana, Laranja, Uva";
    }
    
    @PostMapping
    public String criarProduto(@RequestBody String produto) {
        return "Produto criado: " + produto;
    }
    
    @GetMapping("/{id}")
    public String buscarProduto(@PathVariable Long id) {
        return "Produto ID " + id;
    }
    
    @DeleteMapping("/{id}")
    public String removerProduto(@PathVariable Long id) {
        return "Produto " + id + " removido";
    }
}