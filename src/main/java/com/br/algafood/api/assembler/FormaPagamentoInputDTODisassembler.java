package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.algafood.api.model.input.FormaPagamentoInputDTO;
import com.br.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDTODisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamento toDomainObject(FormaPagamentoInputDTO formaPagamentoInput) {
		return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
	}
	
	public void copyToDomainObject(FormaPagamentoInputDTO formaPagamentoInput, FormaPagamento formaPagamento) {
		modelMapper.map(formaPagamentoInput, formaPagamento);
	}
}
