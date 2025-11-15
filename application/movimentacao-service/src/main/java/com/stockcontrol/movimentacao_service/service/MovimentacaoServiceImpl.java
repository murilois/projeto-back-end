package com.stockcontrol.movimentacao_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stockcontrol.movimentacao_service.client.ProdutoClient;
import com.stockcontrol.movimentacao_service.client.ProdutoDTO;
import com.stockcontrol.movimentacao_service.client.UsuarioClient;
import com.stockcontrol.movimentacao_service.client.UsuarioDTO;
import com.stockcontrol.movimentacao_service.domain.Movimentacao;
import com.stockcontrol.movimentacao_service.domain.TipoMovimentacao;
import com.stockcontrol.movimentacao_service.dto.MovimentacaoRequestDTO;
import com.stockcontrol.movimentacao_service.dto.MovimentacaoResponseDTO;
import com.stockcontrol.movimentacao_service.repository.MovimentacaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimentacaoServiceImpl implements MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final UsuarioClient usuarioClient;
    private final ProdutoClient produtoClient;

    @Override
    @Transactional
    public MovimentacaoResponseDTO criarMovimentacao(MovimentacaoRequestDTO dto) {
        UsuarioDTO usuario = usuarioClient.buscarUsuarioPorId(dto.getUsuarioId());
        ProdutoDTO produto = produtoClient.buscarProdutoPorId(dto.getProdutoId());

        BigDecimal delta = calcularDelta(dto.getTipoMovimentacao(), dto.getQuantidade());
        produtoClient.atualizarEstoque(dto.getProdutoId(), delta);

        Movimentacao movimentacao = Movimentacao.builder()
                .produtoId(dto.getProdutoId())
                .usuarioId(dto.getUsuarioId())
                .tipoMovimentacao(dto.getTipoMovimentacao())
                .quantidade(dto.getQuantidade())
                .motivo(dto.getMotivo())
                .dataMovimentacao(LocalDateTime.now())
                .build();

        Movimentacao salvo = movimentacaoRepository.save(movimentacao);
        return mapToDto(salvo, produto, usuario);
    }

    @Override
    public List<MovimentacaoResponseDTO> listarPorProduto(Long produtoId) {
        return movimentacaoRepository.findByProdutoId(produtoId)
                .stream()
                .map(mov -> mapToDto(mov, null, null))
                .collect(Collectors.toList());
    }

    private BigDecimal calcularDelta(TipoMovimentacao tipo, BigDecimal quantidade) {
        return tipo == TipoMovimentacao.SAIDA ? quantidade.negate() : quantidade;
    }

    private MovimentacaoResponseDTO mapToDto(Movimentacao mov, ProdutoDTO produto, UsuarioDTO usuario) {
        return MovimentacaoResponseDTO.builder()
                .id(mov.getId())
                .tipoMovimentacao(mov.getTipoMovimentacao())
                .quantidade(mov.getQuantidade())
                .motivo(mov.getMotivo())
                .dataMovimentacao(mov.getDataMovimentacao())
                .produto(produto)
                .usuario(usuario)
                .quantidadeAnterior(mov.getQuantidadeAnterior())
                .quantidadeAtual(mov.getQuantidadeAtual())
                .build();
    }
}
