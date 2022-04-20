package com.br.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.br.algafood.api.model.PedidoResumoDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("PedidosResumoDTO")
public class PedidosResumoModelOpenApi{

	private PedidosResumoEmbeddedModelOpenApi _embedded;
    private Links _links;
    private PagedModelOpenApi page;
    
    @ApiModel("PedidosResumoEmbeddedModel")
    @Data
    public class PedidosResumoEmbeddedModelOpenApi {
        
        private List<PedidoResumoDTO> pedidos;
        
    }   
}
