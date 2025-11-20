package com.stockcontrol.service;

import com.stockcontrol.dto.EstoqueDTO;
import com.stockcontrol.model.Estoque;
import com.stockcontrol.model.Produto;
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
    public void iniciarEstoque(Produto produto) {
        if (repository.findByProdutoId(produto.getId()).isPresent()) {
            return;
        }

        Estoque estoque = new Estoque();
        estoque.setProduto(produto);
        estoque.setQuantidadeAtual(BigDecimal.ZERO);
        estoque.setQuantidadeMinima(BigDecimal.ZERO);
        
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