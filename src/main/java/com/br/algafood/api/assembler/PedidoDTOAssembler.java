package com.br.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.controller.PedidoController;
import com.br.algafood.api.model.PedidoDTO;
import com.br.algafood.core.security.AlgaSecurity;
import com.br.algafood.domain.model.Pedido;

@Component
public class PedidoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public PedidoDTOAssembler() {
		super(PedidoController.class, PedidoDTO.class);
	}

	@Override
	public PedidoDTO toModel(Pedido pedido) {
		PedidoDTO pedidoModel = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoModel);

		if (algaSecurity.podePesquisarPedidos()) {
			pedidoModel.add(algaLinks.linkToPedidos("pedidos"));
		}
		if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
			if (pedido.podeSerConfirmado()) {
				pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), "confirmar"));
			}

			if (pedido.podeSerCancelado()) {
				pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), "cancelar"));
			}

			if (pedido.podeSerEntregue()) {
				pedidoModel.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(), "entregar"));
			}

		}

		if (algaSecurity.podeConsultarRestaurantes()) {
	        pedidoModel.getRestaurante().add(
	                algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
	    }
	    
	    if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
	        pedidoModel.getCliente().add(
	                algaLinks.linkToUsuario(pedido.getCliente().getId()));
	    }
	    
	    if (algaSecurity.podeConsultarFormasPagamento()) {
	        pedidoModel.getFormaPagamento().add(
	                algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
	    }
	    
	    if (algaSecurity.podeConsultarCidades()) {
	        pedidoModel.getEnderecoEntrega().getCidade().add(
	                algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));
	    }
	    
	    if (algaSecurity.podeConsultarRestaurantes()) {
	        pedidoModel.getItens().forEach(item -> {
	            item.add(algaLinks.linkToProduto(
	                    pedidoModel.getRestaurante().getId(), item.getProdutoId(), "produto"));
	        });
	    }

		return pedidoModel;
	}

	public List<PedidoDTO> toCollectionModel(List<Pedido> pedidos) {
		return pedidos.stream().map(pedido -> toModel(pedido)).collect(Collectors.toList());
	}
}
