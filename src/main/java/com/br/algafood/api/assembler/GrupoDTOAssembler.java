package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.controller.GrupoController;
import com.br.algafood.api.model.GrupoDTO;
import com.br.algafood.domain.model.Grupo;

@Component
public class GrupoDTOAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public GrupoDTOAssembler() {
		super(GrupoController.class, GrupoDTO.class);
	}
	
	@Override
	public GrupoDTO toModel(Grupo grupo) {
		GrupoDTO grupoDTO = createModelWithId(grupo.getId(), grupo);
		modelMapper.map(grupo, grupoDTO);
		
		grupoDTO.add(algaLinks.linkToGrupos("grupos"));
		grupoDTO.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
		
		return grupoDTO;
	}
	
	@Override
    public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToGrupos());
    }   
	
//	public List<GrupoDTO> toCollectionModel(Collection<Grupo> grupos) {
//		return grupos.stream()
//				.map(grupo -> toModel(grupo))
//				.collect(Collectors.toList());
//	}
}
