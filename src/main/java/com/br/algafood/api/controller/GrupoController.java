package com.br.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.algafood.api.assembler.GrupoDTOAssembler;
import com.br.algafood.api.assembler.GrupoInputDTODisassembler;
import com.br.algafood.api.model.GrupoDTO;
import com.br.algafood.api.model.input.GrupoInputDTO;
import com.br.algafood.api.openapi.controller.GrupoControllerOpenApi;
import com.br.algafood.core.security.CheckSecurity;
import com.br.algafood.domain.model.Grupo;
import com.br.algafood.domain.repository.GrupoRepository;
import com.br.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController implements GrupoControllerOpenApi {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private GrupoDTOAssembler assembler;
	
	@Autowired
	private GrupoInputDTODisassembler disassembler;
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<GrupoDTO> listar() {
		return assembler.toCollectionModel(grupoRepository.findAll());
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping(path = "/{grupoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GrupoDTO buscar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		return assembler.toModel(grupo);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar(@RequestBody @Valid GrupoInputDTO grupoInput) {
		Grupo grupo = disassembler.toDomainObject(grupoInput);
		grupo = cadastroGrupo.salvar(grupo);
		return assembler.toModel(grupo);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PutMapping(path = "/{grupoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public GrupoDTO atualizar(@PathVariable Long grupoId,
			@RequestBody @Valid GrupoInputDTO grupoInput) {
		Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);
		disassembler.copyToDomainObject(grupoInput, grupoAtual);
		grupoAtual = cadastroGrupo.salvar(grupoAtual);
		
		//BeanUtils.copyProperties(grupo, grupoAtual, "id");
		
		return assembler.toModel(grupoAtual);
	}
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {
		cadastroGrupo.excluir(grupoId);	
	}
	
}