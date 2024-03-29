package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.controller.PermissaoController;
import com.br.algafood.api.model.PermissaoDTO;
import com.br.algafood.core.security.AlgaSecurity;
import com.br.algafood.domain.model.Permissao;

@Component
public class PermissaoDTOAssembler extends RepresentationModelAssemblerSupport<Permissao, PermissaoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;
	
	@Autowired
	private AlgaSecurity algaSecurity;   

	public PermissaoDTOAssembler() {
		super(PermissaoController.class, PermissaoDTO.class);
	}

	@Override
	public PermissaoDTO toModel(Permissao permissao) {
		PermissaoDTO permissaoModel = modelMapper.map(permissao, PermissaoDTO.class);
		return permissaoModel;
	}

	@Override
	public CollectionModel<PermissaoDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
		 CollectionModel<PermissaoDTO> collectionModel 
	        = super.toCollectionModel(entities);

	    if (algaSecurity.podeConsultarUsuariosGruposPermissoes()) {
	        collectionModel.add(algaLinks.linkToPermissoes());
	    }
	    
	    return collectionModel;  
	}

//	public List<PermissaoDTO> toCollectionModel(Collection<Permissao> permissoes) {
//		return permissoes.stream()
//				.map(permissao -> toModel(permissao))
//				.collect(Collectors.toList());
//	}
}
