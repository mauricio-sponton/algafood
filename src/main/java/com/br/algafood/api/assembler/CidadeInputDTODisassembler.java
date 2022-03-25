package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.algafood.api.model.input.CidadeInputDTO;
import com.br.algafood.domain.model.Cidade;
import com.br.algafood.domain.model.Estado;

@Component
public class CidadeInputDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomainObject(CidadeInputDTO cidadeInput) {
		return modelMapper.map(cidadeInput, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInputDTO cidadeInput, Cidade cidade) {
		cidade.setEstado(new Estado());
		modelMapper.map(cidadeInput, cidade);
	}
}
