package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.algafood.api.model.input.UsuarioInputDTO;
import com.br.algafood.domain.model.Usuario;

@Component
public class UsuarioInputDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainObject(UsuarioInputDTO usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioInputDTO usuarioInput, Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
}
