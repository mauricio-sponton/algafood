package com.br.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaDTO {

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Tailandesa")
	private String nome;
}
