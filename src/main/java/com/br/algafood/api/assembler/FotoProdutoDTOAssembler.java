package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.controller.ProdutoFotoController;
import com.br.algafood.api.model.FotoProdutoDTO;
import com.br.algafood.core.security.AlgaSecurity;
import com.br.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoDTOAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public FotoProdutoDTOAssembler() {
		super(ProdutoFotoController.class, FotoProdutoDTO.class);
	}

	@Override
	public FotoProdutoDTO toModel(FotoProduto foto) {
		FotoProdutoDTO fotoProdutoModel = modelMapper.map(foto, FotoProdutoDTO.class);

		if (algaSecurity.podeConsultarRestaurantes()) {
			fotoProdutoModel.add(algaLinks.linkToFotoProduto(foto.getRestauranteId(), foto.getProduto().getId()));

			fotoProdutoModel
					.add(algaLinks.linkToProduto(foto.getRestauranteId(), foto.getProduto().getId(), "produto"));
		}

		return fotoProdutoModel;
	}

}
