package com.br.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaInputDTO {

	@ApiModelProperty(example = "Tailandesa", required = true)
	@NotBlank
	private String nome;
	
}
