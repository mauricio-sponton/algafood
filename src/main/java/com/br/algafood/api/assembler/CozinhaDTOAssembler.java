package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.controller.CozinhaController;
import com.br.algafood.api.model.CozinhaDTO;
import com.br.algafood.core.security.AlgaSecurity;
import com.br.algafood.domain.model.Cozinha;

@Component
public class CozinhaDTOAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public CozinhaDTOAssembler() {
		super(CozinhaController.class, CozinhaDTO.class);
	}

	@Override
	public CozinhaDTO toModel(Cozinha cozinha) {
		CozinhaDTO cozinhaDTO = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaDTO);

		if (algaSecurity.podeConsultarCozinhas()) {
			cozinhaDTO.add(algaLinks.linkToCozinhas("cozinhas"));
		}
		return cozinhaDTO;
	}
//	
//	public List<CozinhaDTO> toCollectionModel(List<Cozinha> cozinhas) {
//		return cozinhas.stream()
//				.map(cozinha -> toModel(cozinha))
//				.collect(Collectors.toList());
//	}
}
