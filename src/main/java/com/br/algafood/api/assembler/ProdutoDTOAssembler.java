package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.controller.RestauranteProdutoController;
import com.br.algafood.api.model.ProdutoDTO;
import com.br.algafood.core.security.AlgaSecurity;
import com.br.algafood.domain.model.Produto;

@Component
public class ProdutoDTOAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;

	public ProdutoDTOAssembler() {
		super(RestauranteProdutoController.class, ProdutoDTO.class);
	}

	@Override
	public ProdutoDTO toModel(Produto produto) {
		ProdutoDTO produtoModel = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());

		modelMapper.map(produto, produtoModel);

		if (algaSecurity.podeConsultarRestaurantes()) {
			produtoModel.add(algaLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
			
			produtoModel.add(algaLinks.linkToFotoProduto(
		            produto.getRestaurante().getId(), produto.getId(), "foto"));
		}

		return produtoModel;
	}

//	public List<ProdutoDTO> toCollectionModel(List<Produto> produtos) {
//		return produtos.stream()
//				.map(produto -> toModel(produto))
//				.collect(Collectors.toList());
//	}
}
