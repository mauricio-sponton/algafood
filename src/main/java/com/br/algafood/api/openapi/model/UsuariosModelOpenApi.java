package com.br.algafood.api.openapi.model;

import java.util.List;

import org.springframework.hateoas.Links;

import com.br.algafood.api.model.UsuarioDTO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("UsuariosModel")
@Data
public class UsuariosModelOpenApi {

    private UsuariosEmbeddedModelOpenApi _embedded;
    private Links _links;
    
    @ApiModel("UsuariosEmbeddedModel")
    @Data
    public class UsuariosEmbeddedModelOpenApi {
        
        private List<UsuarioDTO> usuarios;
        
    }   
}   