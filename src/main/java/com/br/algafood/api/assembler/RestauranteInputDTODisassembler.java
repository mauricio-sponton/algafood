package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.algafood.api.model.input.RestauranteInputDTO;
import com.br.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInputDTO restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
}
