package com.br.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.br.algafood.api.controller.PedidoController;
import com.br.algafood.api.controller.RestauranteController;
import com.br.algafood.api.controller.UsuarioController;
import com.br.algafood.api.model.PedidoResumoDTO;
import com.br.algafood.domain.model.Pedido;

@Component
public class PedidoResumoDTOAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	public PedidoResumoDTOAssembler() {
		super(PedidoController.class, PedidoResumoDTO.class);
	}

	@Override
	public PedidoResumoDTO toModel(Pedido pedido) {
		PedidoResumoDTO pedidoResumoDTO = createModelWithId(pedido.getCodigo(), pedido);
		modelMapper.map(pedido, pedidoResumoDTO);

		pedidoResumoDTO.add(linkTo(PedidoController.class).withRel("pedidos"));

		pedidoResumoDTO.getRestaurante().add(
				linkTo(methodOn(RestauranteController.class).buscar(pedido.getRestaurante().getId())).withSelfRel());

		pedidoResumoDTO.getCliente()
				.add(linkTo(methodOn(UsuarioController.class).buscar(pedido.getCliente().getId())).withSelfRel());

		return pedidoResumoDTO;
	}

	public List<PedidoResumoDTO> toCollectionModel(List<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toModel(pedido))
				.collect(Collectors.toList());
	}
}
