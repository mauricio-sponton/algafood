package com.br.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Mauricio")
	private String nome;
	
	@ApiModelProperty(example = "mauricio@gmail.com")
	private String email;
}
