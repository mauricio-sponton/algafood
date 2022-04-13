package com.br.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.controller.PedidoController;
import com.br.algafood.api.model.PedidoResumoDTO;
import com.br.algafood.domain.model.Pedido;

@Component
public class PedidoResumoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public PedidoResumoDTOAssembler() {
		super(PedidoController.class, PedidoResumoDTO.class);
	}

	@Override
	public PedidoResumoDTO toModel(Pedido pedido) {
		PedidoResumoDTO pedidoResumoDTO = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoResumoDTO);

		pedidoResumoDTO.add(algaLinks.linkToPedidos("pedidos"));

		pedidoResumoDTO.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

		pedidoResumoDTO.getCliente()
				.add(algaLinks.linkToUsuario(pedido.getCliente().getId()));

		return pedidoResumoDTO;
	}

	public List<PedidoResumoDTO> toCollectionModel(List<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toModel(pedido))
				.collect(Collectors.toList());
	}
}
