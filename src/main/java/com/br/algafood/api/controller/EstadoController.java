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

import com.br.algafood.api.assembler.EstadoDTOAssembler;
import com.br.algafood.api.assembler.EstadoInputDTODisassembler;
import com.br.algafood.api.model.EstadoDTO;
import com.br.algafood.api.model.input.EstadoInputDTO;
import com.br.algafood.domain.model.Estado;
import com.br.algafood.domain.repository.EstadoRepository;
import com.br.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@Autowired
	private EstadoDTOAssembler assembler;
	
	@Autowired
	private EstadoInputDTODisassembler disassembler;
	
	@GetMapping
	public List<EstadoDTO> listar() {
		return assembler.toCollectionModel(estadoRepository.findAll());
	}
	
	@GetMapping("/{estadoId}")
	public EstadoDTO buscar(@PathVariable Long estadoId) {
		Estado estado = cadastroEstado.buscarOuFalhar(estadoId);
		return assembler.toModel(estado);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoInputDTO estadoInput) {
		Estado estado = disassembler.toDomainObject(estadoInput);
		estado = cadastroEstado.salvar(estado);
		return assembler.toModel(estado);
	}
	
	@PutMapping("/{estadoId}")
	public EstadoDTO atualizar(@PathVariable Long estadoId,
			@RequestBody @Valid EstadoInputDTO estadoInput) {
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
		disassembler.copyToDomainObject(estadoInput, estadoAtual);
		estadoAtual = cadastroEstado.salvar(estadoAtual);
		
		//BeanUtils.copyProperties(estado, estadoAtual, "id");
		
		return assembler.toModel(estadoAtual);
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {
		cadastroEstado.excluir(estadoId);	
	}
	
}