package com.br.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeDTO extends RepresentationModel<CidadeDTO>{

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Uberlândia")
	private String nome;
	private EstadoDTO estado;
}
