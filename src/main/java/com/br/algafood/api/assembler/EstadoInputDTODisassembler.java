package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.algafood.api.model.input.EstadoInputDTO;
import com.br.algafood.domain.model.Estado;

@Component
public class EstadoInputDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Estado toDomainObject(EstadoInputDTO estadoInput) {
		return modelMapper.map(estadoInput, Estado.class);
	}
	
	public void copyToDomainObject(EstadoInputDTO estadoInput, Estado estado) {
		modelMapper.map(estadoInput, estado);
	}
}
