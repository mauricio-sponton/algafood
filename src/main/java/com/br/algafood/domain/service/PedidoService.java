package com.br.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.algafood.domain.exception.PedidoNaoEncontradoException;
import com.br.algafood.domain.model.Pedido;
import com.br.algafood.domain.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	public Pedido buscarOuFalhar(Long pedidoId) {
		return pedidoRepository.findById(pedidoId)
			.orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
	}
	
}