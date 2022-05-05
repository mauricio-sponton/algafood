package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.controller.CidadeController;
import com.br.algafood.api.model.CidadeDTO;
import com.br.algafood.core.security.AlgaSecurity;
import com.br.algafood.domain.model.Cidade;

@Component
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public CidadeDTOAssembler() {
		super(CidadeController.class, CidadeDTO.class);
	}

	@Override
	public CidadeDTO toModel(Cidade cidade) {

		CidadeDTO cidadeDTO = createModelWithId(cidade.getId(), cidade);

		modelMapper.map(cidade, cidadeDTO);
		if (algaSecurity.podeConsultarCidades()) {
			cidadeDTO.add(algaLinks.linkToCidades("cidades"));
		}
		if (algaSecurity.podeConsultarEstados()) {
			cidadeDTO.getEstado().add(algaLinks.linkToEstado(cidadeDTO.getEstado().getId()));
		}
		return cidadeDTO;
	}

	@Override
	public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
		CollectionModel<CidadeDTO> collectionModel = super.toCollectionModel(entities);

		if (algaSecurity.podeConsultarCidades()) {
			collectionModel.add(algaLinks.linkToCidades());
		}

		return collectionModel;
	}

//	public List<CidadeDTO> toCollectionModel(List<Cidade> cidades) {
//		return cidades.stream().map(cidade -> toModel(cidade)).collect(Collectors.toList());
//	}
}
