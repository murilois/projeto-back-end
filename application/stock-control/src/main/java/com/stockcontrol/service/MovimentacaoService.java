package com.stockcontrol.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockcontrol.dto.MovimentacaoDTO;
import com.stockcontrol.model.Movimentacao;
import com.stockcontrol.model.Produto;
import com.stockcontrol.model.Usuario;
import com.stockcontrol.model.enums.PerfilUsuario;
import com.stockcontrol.repository.MovimentacaoRepository;
import com.stockcontrol.repository.ProdutoRepository;
import com.stockcontrol.repository.UsuarioRepository;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstoqueService estoqueService;

    @Transactional
    public Movimentacao registrarMovimentacao(MovimentacaoDTO dto) {
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado."));

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        // Apenas ADMIN faz AJUSTE ---
        if (dto.getTipo() == com.stockcontrol.model.enums.TipoMovimentacao.AJUSTE) {
            if (usuario.getPerfil() != PerfilUsuario.ADMIN) {
                throw new IllegalArgumentException("Apenas usuários ADMIN podem realizar AJUSTES de estoque.");
            }
        }

        estoqueService.atualizarSaldo(produto.getId(), dto.getQuantidade(), dto.getTipo());

        Movimentacao movimentacao = new Movimentacao();
        BeanUtils.copyProperties(dto, movimentacao);
        movimentacao.setProduto(produto);
        movimentacao.setUsuario(usuario);

        return repository.save(movimentacao);
    }
}