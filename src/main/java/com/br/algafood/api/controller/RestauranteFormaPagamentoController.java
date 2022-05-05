package com.br.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.br.algafood.api.model.FormaPagamentoDTO;
import com.br.algafood.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.br.algafood.core.security.AlgaSecurity;
import com.br.algafood.core.security.CheckSecurity;
import com.br.algafood.domain.model.Restaurante;
import com.br.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private FormaPagamentoDTOAssembler assembler;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

		CollectionModel<FormaPagamentoDTO> formasPagamentoModel = assembler
				.toCollectionModel(restaurante.getFormasPagamento()).removeLinks();
		formasPagamentoModel.add(algaLinks.linkToRestauranteFormasPagamento(restauranteId));

		if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restauranteId)) {
			formasPagamentoModel.add(algaLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));

			formasPagamentoModel.getContent().forEach(formaPagamentoModel -> {
				formaPagamentoModel.add(algaLinks.linkToRestauranteFormaPagamentoDesassociacao(restauranteId,
						formaPagamentoModel.getId(), "desassociar"));
			});
		}

		return formasPagamentoModel;
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@DeleteMapping(path = "/{formaPagamentoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestaurante.desassociarFormaPagamento(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PutMapping(path = "/{formaPagamentoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		cadastroRestaurante.associarFormaPagamento(restauranteId, formaPagamentoId);
		return ResponseEntity.noContent().build();
	}

}
