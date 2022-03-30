package com.br.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.br.algafood.api.model.EnderecoDTO;
import com.br.algafood.api.model.input.ItemPedidoInputDTO;
import com.br.algafood.domain.model.Endereco;
import com.br.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelmapper = new ModelMapper();
		
		var enderecoToEnderecoDTO = modelmapper.createTypeMap(Endereco.class, EnderecoDTO.class);

		modelmapper.createTypeMap(ItemPedidoInputDTO.class, ItemPedido.class)
	    .addMappings(mapper -> mapper.skip(ItemPedido::setId));  
		
		enderecoToEnderecoDTO.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(destinoEnderecoDTO, valor) -> destinoEnderecoDTO.getCidade().setEstado(valor));
		
		return modelmapper;
	}
	
}