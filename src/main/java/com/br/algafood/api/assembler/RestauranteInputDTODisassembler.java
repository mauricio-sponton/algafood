package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.algafood.api.model.input.RestauranteInputDTO;
import com.br.algafood.domain.model.Cidade;
import com.br.algafood.domain.model.Cozinha;
import com.br.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInputDTO restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInputDTO restauranteInput, Restaurante restaurante) {
		restaurante.setCozinha(new Cozinha());
		if(restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		modelMapper.map(restauranteInput, restaurante);
	}
}
