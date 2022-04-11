package com.br.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.controller.EstadoController;
import com.br.algafood.api.model.EstadoDTO;
import com.br.algafood.domain.model.Estado;

@Component
public class EstadoDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public EstadoDTOAssembler() {
		super(EstadoController.class, EstadoDTO.class);
	}
	
	@Override
	public EstadoDTO toModel(Estado estado) {
		EstadoDTO estadoDTO = createModelWithId(estado.getId(), estado);
		modelMapper.map(estado, estadoDTO);
		
		estadoDTO.add(algaLinks.linkToEstados("estados"));
		
		return estadoDTO;
	}
	
//	public List<EstadoDTO> toCollectionModel(List<Estado> estados) {
//		return estados.stream()
//				.map(estado -> toModel(estado))
//				.collect(Collectors.toList());
//	}
	
	@Override
    public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
            .add(linkTo(EstadoController.class).withSelfRel());
    }   
}
