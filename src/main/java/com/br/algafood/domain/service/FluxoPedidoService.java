package com.br.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.algafood.domain.model.Pedido;
import com.br.algafood.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;

	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.confirmar();
		
		pedidoRepository.save(pedido);
		
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.cancelar();
		
		pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void entregue(String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.entregar();
	}
}
