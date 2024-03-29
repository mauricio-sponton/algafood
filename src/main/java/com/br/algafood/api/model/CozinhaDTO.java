package com.br.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaDTO extends RepresentationModel<CozinhaDTO>{

	@ApiModelProperty(example = "1")
	private Long id;

	@ApiModelProperty(example = "Tailandesa")
	private String nome;
}
