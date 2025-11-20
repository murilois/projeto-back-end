package com.stockcontrol.service;

import com.stockcontrol.dto.EstoqueDTO;
import com.stockcontrol.model.Estoque;
import com.stockcontrol.model.Produto;
import com.stockcontrol.model.enums.TipoMovimentacao; // Importante
import com.stockcontrol.repository.EstoqueRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository repository;

    @Transactional
    public void iniciarEstoque(Produto produto, BigDecimal estoqueMinimo) {
        if (repository.findByProdutoId(produto.getId()).isPresent()) return;

        Estoque estoque = new Estoque();
        estoque.setProduto(produto);
        estoque.setQuantidadeAtual(BigDecimal.ZERO);
        estoque.setQuantidadeMinima(estoqueMinimo != null ? estoqueMinimo : BigDecimal.ZERO);
        
        repository.save(estoque);
    }

    @Transactional
    public void atualizarSaldo(UUID produtoId, BigDecimal quantidade, TipoMovimentacao tipo) {
        Estoque estoque = repository.findByProdutoId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado."));

        if (tipo == TipoMovimentacao.ENTRADA) {
            estoque.adicionar(quantidade);
        } else if (tipo == TipoMovimentacao.SAIDA) {
            estoque.remover(quantidade);
        } else if (tipo == TipoMovimentacao.AJUSTE) {
            // NOVO: Lógica do Ajuste (Define o valor exato)
            if (quantidade.compareTo(BigDecimal.ZERO) < 0) 
                throw new IllegalArgumentException("Ajuste não pode ser negativo.");
            estoque.setQuantidadeAtual(quantidade);
        }
        
        repository.save(estoque);
    }

    public EstoqueDTO buscarPorProdutoId(UUID produtoId) {
        Estoque estoque = repository.findByProdutoId(produtoId)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado para este produto."));
        return converterParaDTO(estoque);
    }
    
    private EstoqueDTO converterParaDTO(Estoque estoque) {
        EstoqueDTO dto = new EstoqueDTO();
        BeanUtils.copyProperties(estoque, dto);
        dto.setProdutoId(estoque.getProduto().getId());
        dto.setProdutoNome(estoque.getProduto().getNome());
        return dto;
    }
}