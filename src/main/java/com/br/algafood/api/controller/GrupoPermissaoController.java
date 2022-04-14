package com.br.algafood.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.assembler.PermissaoDTOAssembler;
import com.br.algafood.api.model.PermissaoDTO;
import com.br.algafood.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.br.algafood.domain.model.Grupo;
import com.br.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	@Autowired
	private CadastroGrupoService cadastroGrupoService;

	@Autowired
	private PermissaoDTOAssembler assembler;

	@Autowired
	private AlgaLinks algaLinks;

	@Override
	@GetMapping
	public CollectionModel<PermissaoDTO> listar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);

		CollectionModel<PermissaoDTO> permissoesModel = assembler.toCollectionModel(grupo.getPermissoes()).removeLinks()
				.add(algaLinks.linkToGrupoPermissoes(grupoId))
				.add(algaLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

		permissoesModel.getContent().forEach(permissaoModel -> {
			permissaoModel
					.add(algaLinks.linkToGrupoPermissaoDesassociacao(grupoId, permissaoModel.getId(), "desassociar"));
		});

		return permissoesModel;
	}

	@Override
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupoService.desassociarPermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupoService.associarPermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}

}
