package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/relatorios")
public class RelatorioController {
    
    @GetMapping("/estoque")
    public String relatorioEstoque() {
        return "Relatório de Estoque: 25 produtos cadastrados";
    }
    
    @GetMapping("/produtos")
    public String relatorioProdutos() {
        return "Relatório de Produtos: Maçã, Banana, Laranja, Uva";
    }
    
    @GetMapping("/movimentacoes")
    public String relatorioMovimentacoes() {
        return "Relatório de Movimentações: 15 entradas, 8 saídas";
    }
    
    @GetMapping("/vendas")
    public String relatorioVendas() {
        return "Relatório de Vendas: R$ 1.250,00 no mês";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "Dashboard: Estoque OK, 3 produtos em falta";
    }
}