package com.br.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.br.algafood.api.model.mixin.CidadeMixin;
import com.br.algafood.api.model.mixin.CozinhaMixin;
import com.br.algafood.api.model.mixin.RestauranteMixin;
import com.br.algafood.domain.model.Cidade;
import com.br.algafood.domain.model.Cozinha;
import com.br.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule{
	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
	}

	
}
