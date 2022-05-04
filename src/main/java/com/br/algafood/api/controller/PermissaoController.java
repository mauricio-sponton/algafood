package com.br.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.algafood.api.assembler.PermissaoDTOAssembler;
import com.br.algafood.api.model.PermissaoDTO;
import com.br.algafood.api.openapi.controller.PermissaoControllerOpenApi;
import com.br.algafood.core.security.CheckSecurity;
import com.br.algafood.domain.model.Permissao;
import com.br.algafood.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(value = "/permissoes")
public class PermissaoController implements PermissaoControllerOpenApi  {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	@Autowired
	private PermissaoDTOAssembler assembler;
	
	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
    public CollectionModel<PermissaoDTO> listar() {
        List<Permissao> todasPermissoes = permissaoRepository.findAll();
        return assembler.toCollectionModel(todasPermissoes);
    }   
	
}