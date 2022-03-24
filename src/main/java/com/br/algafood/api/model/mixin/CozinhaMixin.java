package com.br.algafood.api.model.mixin;

import java.util.List;

import com.br.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class CozinhaMixin {

	@JsonIgnore
	private List<Restaurante> restaurantes;
}
