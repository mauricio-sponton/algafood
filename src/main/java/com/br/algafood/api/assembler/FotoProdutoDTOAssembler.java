package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.algafood.api.model.FotoProdutoDTO;
import com.br.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FotoProdutoDTO toModel(FotoProduto fotoProduto) {
		return modelMapper.map(fotoProduto, FotoProdutoDTO.class);
	}
	
}
