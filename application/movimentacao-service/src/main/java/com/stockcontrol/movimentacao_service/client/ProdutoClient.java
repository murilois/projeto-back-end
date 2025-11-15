package com.stockcontrol.movimentacao_service.client;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "produto-service")
public interface ProdutoClient {

    @GetMapping("/api/v1/produtos/{id}")
    ProdutoDTO buscarProdutoPorId(@PathVariable("id") Long id);

    @PostMapping("/api/v1/produtos/{id}/estoque/atualizar")
    void atualizarEstoque(@PathVariable("id") Long id, @RequestBody BigDecimal quantidadeDelta);
}
