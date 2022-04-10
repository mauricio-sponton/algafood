package com.br.algafood.api.controller;

import java.util.List;

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

import com.br.algafood.api.ResourceUriHelper;
import com.br.algafood.api.assembler.CidadeDTOAssembler;
import com.br.algafood.api.assembler.CidadeInputDTODisassembler;
import com.br.algafood.api.model.CidadeDTO;
import com.br.algafood.api.model.input.CidadeInputDTO;
import com.br.algafood.api.openapi.controller.CidadeControllerOpenApi;
import com.br.algafood.domain.exception.EstadoNaoEncontradoException;
import com.br.algafood.domain.exception.NegocioException;
import com.br.algafood.domain.model.Cidade;
import com.br.algafood.domain.repository.CidadeRepository;
import com.br.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(path = "/cidades")
public class CidadeController implements CidadeControllerOpenApi {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@Autowired
	private CidadeDTOAssembler assembler;

	@Autowired
	private CidadeInputDTODisassembler disassembler;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<CidadeDTO> listar() {
		List<Cidade> cidades = cidadeRepository.findAll();
		return assembler.toCollectionModel(cidades);
	}

	@Override
	@GetMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CidadeDTO buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		return assembler.toModel(cidade);
	}

	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInputDTO cidadeInput) {
		try {
			Cidade cidade = disassembler.toDomainObject(cidadeInput);
			cidade = cadastroCidade.salvar(cidade);
			
			CidadeDTO cidadeDTO = assembler.toModel(cidade);
			
			ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());

			return cidadeDTO;
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Override
	@PutMapping(path = "/{cidadeId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CidadeDTO atualizar(@PathVariable Long cidadeId, @RequestBody @Valid CidadeInputDTO cidadeInput) {
		try {
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

			disassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			// BeanUtils.copyProperties(cidade, cidadeAtual, "id");

			cidadeAtual = cadastroCidade.salvar(cidadeAtual);

			return assembler.toModel(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@Override
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastroCidade.excluir(cidadeId);
	}

}