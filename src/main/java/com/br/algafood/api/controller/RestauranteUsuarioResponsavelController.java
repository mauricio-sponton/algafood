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
import com.br.algafood.api.assembler.UsuarioDTOAssembler;
import com.br.algafood.api.model.UsuarioDTO;
import com.br.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.br.algafood.core.security.AlgaSecurity;
import com.br.algafood.core.security.CheckSecurity;
import com.br.algafood.domain.model.Restaurante;
import com.br.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private UsuarioDTOAssembler usuarioModelAssembler;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<UsuarioDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

		CollectionModel<UsuarioDTO> usuariosModel = usuarioModelAssembler
				.toCollectionModel(restaurante.getResponsaveis()).removeLinks();
		usuariosModel.add(algaLinks.linkToRestauranteResponsaveis(restauranteId));

		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			usuariosModel.add(algaLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));

			usuariosModel.getContent().stream().forEach(usuarioModel -> {
				usuarioModel.add(algaLinks.linkToRestauranteResponsavelDesassociacao(restauranteId,
						usuarioModel.getId(), "desassociar"));
			});
		}

		return usuariosModel;
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@DeleteMapping(path = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@PutMapping(path = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);
		return ResponseEntity.noContent().build();
	}
}