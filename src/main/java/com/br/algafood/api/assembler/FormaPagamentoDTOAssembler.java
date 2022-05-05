package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.controller.FormaPagamentoController;
import com.br.algafood.api.model.FormaPagamentoDTO;
import com.br.algafood.core.security.AlgaSecurity;
import com.br.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler extends RepresentationModelAssemblerSupport<FormaPagamento, FormaPagamentoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public FormaPagamentoDTOAssembler() {
		super(FormaPagamentoController.class, FormaPagamentoDTO.class);
	}

	@Override
	public FormaPagamentoDTO toModel(FormaPagamento formaPagamento) {
		FormaPagamentoDTO formaPagamentoDTO = createModelWithId(formaPagamento.getId(), formaPagamento);
		modelMapper.map(formaPagamento, formaPagamento);
		if (algaSecurity.podeConsultarFormasPagamento()) {
			formaPagamentoDTO.add(algaLinks.linkToFormasPagamento("formasPagamento"));
		}
		return formaPagamentoDTO;
	}

	@Override
	public CollectionModel<FormaPagamentoDTO> toCollectionModel(Iterable<? extends FormaPagamento> entities) {
		CollectionModel<FormaPagamentoDTO> collectionModel = super.toCollectionModel(entities);

		if (algaSecurity.podeConsultarFormasPagamento()) {
			collectionModel.add(algaLinks.linkToFormasPagamento());
		}

		return collectionModel;
	}

//	public List<FormaPagamentoDTO> toCollectionModel(Collection<FormaPagamento> formaPagamentos) {
//		return formaPagamentos.stream()
//				.map(formaPagamento -> toModel(formaPagamento))
//				.collect(Collectors.toList());
//	}
}
