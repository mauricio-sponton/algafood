package com.br.algafood.api.model.mixin;

import java.time.OffsetDateTime;
import java.util.List;

import com.br.algafood.domain.model.Cozinha;
import com.br.algafood.domain.model.Endereco;
import com.br.algafood.domain.model.FormaPagamento;
import com.br.algafood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class RestauranteMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Cozinha cozinha;
	
	@JsonIgnore
	private Endereco endereco;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
	private OffsetDateTime dataCadastro;
	
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
	private OffsetDateTime dataAtualizacao;
	
	@JsonIgnore
	private List<FormaPagamento> formasPagamento;
	
	@JsonIgnore
	private List<Produto> produtos;
}
