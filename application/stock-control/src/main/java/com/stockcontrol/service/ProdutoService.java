package com.stockcontrol.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockcontrol.dto.ProdutoDTO;
import com.stockcontrol.model.Categoria;
import com.stockcontrol.model.Fornecedor;
import com.stockcontrol.model.Produto;
import com.stockcontrol.repository.CategoriaRepository;
import com.stockcontrol.repository.FornecedorRepository;
import com.stockcontrol.repository.ProdutoRepository;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private EstoqueService estoqueService;
    
    @Autowired
    private FornecedorRepository fornecedorRepository;
    
    @Transactional
    @CacheEvict(value = { "produtos", "produtos_all" }, allEntries = true) // Limpa o cache para obrigar uma nova busca no banco na próxima leitura
    public Produto save(ProdutoDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));

        if (dto.getId() == null && produtoRepository.existsByCodigoBarras(dto.getCodigoBarras())) {
             throw new IllegalArgumentException("Código de barras duplicado.");
        }

        Produto produto = new Produto();
        BeanUtils.copyProperties(dto, produto);
        produto.setCategoria(categoria);

        Produto produtoSalvo = produtoRepository.save(produto);
        
        // Se o produto é novo (id veio null no DTO), cria o estoque zero
        if (dto.getId() == null) {
            estoqueService.iniciarEstoque(produtoSalvo, dto.getEstoqueMinimo());
        }
        if (dto.getFornecedorId() != null) {
            Fornecedor fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                    .orElseThrow(() -> new IllegalArgumentException("Fornecedor não encontrado."));
            produto.setFornecedor(fornecedor);
        }
        return produtoSalvo;
    }
    @Cacheable(value = "produtos_all")
    public List<Produto> findAll() { 
    	System.out.println("Buscando todos os produtos no Banco de Dados");
    	return produtoRepository.findAll(); 
    	}
    @Cacheable(value = "produtos", key = "#id")
    public Optional<Produto> findById(UUID id) {
    	System.out.println("Buscando produto " + id + " no Banco de Dados");
    	return produtoRepository.findById(id); 
    }
    @CacheEvict(value = { "produtos", "produtos_all" }, allEntries = true)
    public void delete(UUID id) { 
    	produtoRepository.deleteById(id); 
    	}
}