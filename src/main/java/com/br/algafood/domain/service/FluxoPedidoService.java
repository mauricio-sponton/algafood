package com.br.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.algafood.domain.model.Pedido;
import com.br.algafood.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private EnvioEmailService envioEmailService;

	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.confirmar();
		
		var mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado!" )
				.corpo("pedido-confirmado.html")
				.variavel("pedido", pedido)
				.destinatario(pedido.getCliente().getEmail())
				.build();
		
		envioEmailService.enviar(mensagem);
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.cancelar();
	}
	
	@Transactional
	public void entregue(String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.entregar();
	}
}
