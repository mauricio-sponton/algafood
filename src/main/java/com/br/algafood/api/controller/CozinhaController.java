package com.br.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

import com.br.algafood.api.assembler.CozinhaDTOAssembler;
import com.br.algafood.api.assembler.CozinhaInputDTODisassembler;
import com.br.algafood.api.model.CozinhaDTO;
import com.br.algafood.api.model.input.CozinhaInputDTO;
import com.br.algafood.api.openapi.controller.CozinhaControllerOpenApi;
import com.br.algafood.domain.model.Cozinha;
import com.br.algafood.domain.repository.CozinhaRepository;
import com.br.algafood.domain.service.CadastroCozinhaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CozinhaDTOAssembler assembler;
	
	@Autowired
	private CozinhaInputDTODisassembler disassembler;
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<CozinhaDTO> listar(Pageable pageable) {
		log.info("Consultando cozinhas com páginas {} de registros...", pageable.getPageSize());
		Page<Cozinha> cozinhas = cozinhaRepository.findAll(pageable);
		
		//List<CozinhaDTO> cozinhasDTO = assembler.toCollectionModel(cozinhas.getContent());
		//Page<CozinhaDTO> cozinhasPage = new PageImpl<CozinhaDTO>(cozinhasDTO, pageable, cozinhas.getTotalElements());
		
		PagedModel<CozinhaDTO> cozinhasPagedModel = pagedResourcesAssembler
				.toModel(cozinhas, assembler);
		
		return cozinhasPagedModel;
	}
	
	@Override
	@GetMapping(path = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		return assembler.toModel(cozinha);
	}
	
	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInputDTO cozinhaInput) {
		Cozinha cozinha = disassembler.toDomainObject(cozinhaInput);
		return assembler.toModel(cadastroCozinha.salvar(cozinha));
	}
	
	@Override
	@PutMapping(path = "/{cozinhaId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId,
			@RequestBody @Valid CozinhaInputDTO cozinhaInput) {
		Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(cozinhaId);
		disassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
		cozinhaAtual = cadastroCozinha.salvar(cozinhaAtual);
		//BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
		
		return assembler.toModel(cozinhaAtual);
	}
	
	@Override
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		cadastroCozinha.excluir(cozinhaId);
	}
}