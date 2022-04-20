package com.br.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.br.algafood.api.model.CozinhaDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@ApiModel("CozinhasDTO")
@Setter
@Getter
public class CozinhasModelOpenApi{

	private CozinhasEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PagedModelOpenApi page;
	
	@ApiModel("CozinhasEmbeddedModel")
	@Data
	public class CozinhasEmbeddedModelOpenApi {
		
		private List<CozinhaDTO> cozinhas;
		
	}
}
