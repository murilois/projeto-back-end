package com.stockcontrol.produto_service.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockcontrol.produto_service.domain.Categoria;
import com.stockcontrol.produto_service.domain.Estoque;
import com.stockcontrol.produto_service.domain.Fornecedor;
import com.stockcontrol.produto_service.domain.Produto;
import com.stockcontrol.produto_service.dto.CategoriaResponseDTO;
import com.stockcontrol.produto_service.dto.EstoqueDTO;
import com.stockcontrol.produto_service.dto.FornecedorResponseDTO;
import com.stockcontrol.produto_service.dto.ProdutoRequestDTO;
import com.stockcontrol.produto_service.dto.ProdutoResponseDTO;
import com.stockcontrol.produto_service.exception.CategoriaNotFoundException;
import com.stockcontrol.produto_service.exception.EstoqueException;
import com.stockcontrol.produto_service.exception.FornecedorNotFoundException;
import com.stockcontrol.produto_service.exception.ProdutoNotFoundException;
import com.stockcontrol.produto_service.repository.CategoriaRepository;
import com.stockcontrol.produto_service.repository.FornecedorRepository;
import com.stockcontrol.produto_service.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;

    @Override
    @Transactional
    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException(dto.getCategoriaId()));
        Fornecedor fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                .orElseThrow(() -> new FornecedorNotFoundException(dto.getFornecedorId()));

        Produto produto = Produto.builder()
                .nome(dto.getNome())
                .codigoBarras(dto.getCodigoBarras())
                .precoCompra(dto.getPrecoCompra())
                .precoVenda(dto.getPrecoVenda())
                .categoria(categoria)
                .fornecedor(fornecedor)
                .build();
        produto.adicionarEstoqueInicial(BigDecimal.ZERO, BigDecimal.ZERO, null);

        Produto salvo = produtoRepository.save(produto);
        return mapToDto(salvo);
    }

    @Override
    public ProdutoResponseDTO buscarPorId(Long id) {
        return produtoRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
    }

    @Override
    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO dto) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException(dto.getCategoriaId()));
        Fornecedor fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                .orElseThrow(() -> new FornecedorNotFoundException(dto.getFornecedorId()));

        produto.setNome(dto.getNome());
        produto.setCodigoBarras(dto.getCodigoBarras());
        produto.setPrecoCompra(dto.getPrecoCompra());
        produto.setPrecoVenda(dto.getPrecoVenda());
        produto.setCategoria(categoria);
        produto.setFornecedor(fornecedor);

        Produto atualizado = produtoRepository.save(produto);
        return mapToDto(atualizado);
    }

    @Override
    @Transactional
    public void deletarProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
        produtoRepository.delete(produto);
    }

    @Override
    @Transactional
    public EstoqueDTO atualizarEstoque(Long produtoId, BigDecimal deltaQuantidade) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNotFoundException(produtoId));
        Estoque estoque = produto.getEstoque();
        if (estoque == null) {
            estoque = new Estoque();
            produto.setEstoque(estoque);
        }
        if (estoque.getQuantidadeAtual() == null) {
            estoque.setQuantidadeAtual(BigDecimal.ZERO);
        }
        BigDecimal novoSaldo = estoque.getQuantidadeAtual().add(deltaQuantidade);
        if (novoSaldo.compareTo(BigDecimal.ZERO) < 0) {
            throw new EstoqueException("Quantidade insuficiente no estoque");
        }
        estoque.setQuantidadeAtual(novoSaldo);
        produto.setEstoque(estoque);
        produtoRepository.save(produto);
        return mapEstoqueToDto(estoque);
    }

    private ProdutoResponseDTO mapToDto(Produto produto) {
        return ProdutoResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .codigoBarras(produto.getCodigoBarras())
                .precoCompra(produto.getPrecoCompra())
                .precoVenda(produto.getPrecoVenda())
                .categoria(buildCategoriaDto(produto))
                .fornecedor(buildFornecedorDto(produto))
                .estoque(mapEstoqueToDto(produto.getEstoque()))
                .build();
    }

    private CategoriaResponseDTO buildCategoriaDto(Produto produto) {
        Categoria categoria = produto.getCategoria();
        if (categoria == null) {
            return null;
        }
        return CategoriaResponseDTO.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .descricao(categoria.getDescricao())
                .ativo(categoria.getAtivo())
                .build();
    }

    private FornecedorResponseDTO buildFornecedorDto(Produto produto) {
        Fornecedor fornecedor = produto.getFornecedor();
        if (fornecedor == null) {
            return null;
        }
        return FornecedorResponseDTO.builder()
                .id(fornecedor.getId())
                .nome(fornecedor.getNome())
                .cnpj(fornecedor.getCnpj())
                .telefone(fornecedor.getTelefone())
                .email(fornecedor.getEmail())
                .ativo(fornecedor.getAtivo())
                .build();
    }

    private EstoqueDTO mapEstoqueToDto(Estoque estoque) {
        if (estoque == null) {
            return null;
        }
        return EstoqueDTO.builder()
                .id(estoque.getId())
                .quantidadeAtual(estoque.getQuantidadeAtual())
                .quantidadeMinima(estoque.getQuantidadeMinima())
                .localizacao(estoque.getLocalizacao())
                .build();
    }
}