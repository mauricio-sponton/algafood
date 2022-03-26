package com.br.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.br.algafood.domain.model.Grupo;
import com.br.algafood.domain.repository.GrupoRepository;
import com.br.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupo;
	
	@Autowired
	private GrupoDTOAssembler assembler;
	
	@Autowired
	private GrupoInputDTODisassembler disassembler;
	
	@GetMapping
	public List<GrupoDTO> listar() {
		return assembler.toCollectionModel(grupoRepository.findAll());
	}
	
	@GetMapping("/{grupoId}")
	public GrupoDTO buscar(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
		return assembler.toModel(grupo);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public GrupoDTO adicionar(@RequestBody @Valid GrupoInputDTO grupoInput) {
		Grupo grupo = disassembler.toDomainObject(grupoInput);
		grupo = cadastroGrupo.salvar(grupo);
		return assembler.toModel(grupo);
	}
	
	@PutMapping("/{grupoId}")
	public GrupoDTO atualizar(@PathVariable Long grupoId,
			@RequestBody @Valid GrupoInputDTO grupoInput) {
		Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(grupoId);
		disassembler.copyToDomainObject(grupoInput, grupoAtual);
		grupoAtual = cadastroGrupo.salvar(grupoAtual);
		
		//BeanUtils.copyProperties(grupo, grupoAtual, "id");
		
		return assembler.toModel(grupoAtual);
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long grupoId) {
		cadastroGrupo.excluir(grupoId);	
	}
	
}