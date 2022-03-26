package com.br.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.br.algafood.api.model.EnderecoDTO;
import com.br.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelmapper = new ModelMapper();
		
		var enderecoToEnderecoDTO = modelmapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		enderecoToEnderecoDTO.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(destinoEnderecoDTO, valor) -> destinoEnderecoDTO.getCidade().setEstado(valor));
		
		return modelmapper;
	}
	
}