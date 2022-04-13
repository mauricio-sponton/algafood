package com.br.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.controller.RestauranteController;
import com.br.algafood.api.model.RestauranteDTO;
import com.br.algafood.domain.model.Restaurante;

@Component
public class RestauranteDTOAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	public RestauranteDTOAssembler() {
		super(RestauranteController.class, RestauranteDTO.class);
	}
	
	@Override
    public RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restauranteModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);
        
        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
        
        restauranteModel.getCozinha().add(
                algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
        
        restauranteModel.getEndereco().getCidade().add(
                algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
        
        restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), 
                "formas-pagamento"));
        
        restauranteModel.add(algaLinks.linkToRestauranteResponsaveis(restaurante.getId(), 
                "responsaveis"));
        
        return restauranteModel;
    }
    
    @Override
    public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }   
	
//	public List<RestauranteDTO> toCollectionModel(List<Restaurante> restaurantes) {
//		return restaurantes.stream()
//				.map(restaurante -> toModel(restaurante))
//				.collect(Collectors.toList());
//	}
}
