package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.algafood.api.model.input.PedidoInputDTO;
import com.br.algafood.domain.model.Pedido;

@Component
public class PedidoInputDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Pedido toDomainObject(PedidoInputDTO pedidoInput) {
		return modelMapper.map(pedidoInput, Pedido.class);
	}
	
	public void copyToDomainObject(PedidoInputDTO pedidoInput, Pedido pedido) {
		modelMapper.map(pedidoInput, pedido);
	}
}
