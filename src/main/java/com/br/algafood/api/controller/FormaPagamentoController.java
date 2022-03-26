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

import com.br.algafood.api.assembler.FormaPagamentoDTOAssembler;
import com.br.algafood.api.assembler.FormaPagamentoInputDTODisassembler;
import com.br.algafood.api.model.FormaPagamentoDTO;
import com.br.algafood.api.model.input.FormaPagamentoInputDTO;
import com.br.algafood.domain.model.FormaPagamento;
import com.br.algafood.domain.repository.FormaPagamentoRepository;
import com.br.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	private FormaPagamentoDTOAssembler assembler;
	
	@Autowired
	private FormaPagamentoInputDTODisassembler disassembler;
	
	@GetMapping
	public List<FormaPagamentoDTO> listar() {
		return assembler.toCollectionModel(formaPagamentoRepository.findAll());
	}
	
	@GetMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO buscar(@PathVariable Long formaPagamentoId) {
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		return assembler.toModel(formaPagamento);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInput) {
		FormaPagamento formaPagamento = disassembler.toDomainObject(formaPagamentoInput);
		formaPagamento = cadastroFormaPagamento.salvar(formaPagamento);
		return assembler.toModel(formaPagamento);
	}
	
	@PutMapping("/{formaPagamentoId}")
	public FormaPagamentoDTO atualizar(@PathVariable Long formaPagamentoId,
			@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInput) {
		FormaPagamento formaPagamentoAtual = cadastroFormaPagamento.buscarOuFalhar(formaPagamentoId);
		disassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
		formaPagamentoAtual = cadastroFormaPagamento.salvar(formaPagamentoAtual);
		
		//BeanUtils.copyProperties(formaPagamento, formaPagamentoAtual, "id");
		
		return assembler.toModel(formaPagamentoAtual);
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long formaPagamentoId) {
		cadastroFormaPagamento.excluir(formaPagamentoId);	
	}
	
}