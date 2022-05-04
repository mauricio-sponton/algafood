package com.br.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.assembler.ProdutoDTOAssembler;
import com.br.algafood.api.assembler.ProdutoInputDTODisassembler;
import com.br.algafood.api.model.ProdutoDTO;
import com.br.algafood.api.model.input.ProdutoInputDTO;
import com.br.algafood.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.br.algafood.core.security.CheckSecurity;
import com.br.algafood.domain.model.Produto;
import com.br.algafood.domain.model.Restaurante;
import com.br.algafood.domain.repository.ProdutoRepository;
import com.br.algafood.domain.service.CadastroProdutoService;
import com.br.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private CadastroProdutoService produtoService;
	
	@Autowired
	private ProdutoDTOAssembler assembler;
	
	@Autowired
	private ProdutoInputDTODisassembler disassembler;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private AlgaLinks algaLinks;
	
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<ProdutoDTO> listar(@PathVariable Long restauranteId, @RequestParam(required = false) Boolean incluirInativos) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		
		List<Produto> produtos = null;
		
		if(incluirInativos) {
			produtos = produtoRepository.findByRestaurante(restaurante);
		}else {
			produtos = produtoRepository.findAtivosByRestaurante(restaurante);
		}
		
		return assembler.toCollectionModel(produtos).add(algaLinks.linkToProdutos(restauranteId));
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping(path = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
		return assembler.toModel(produto);
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO adicionar(@PathVariable Long restauranteId,
            @RequestBody @Valid ProdutoInputDTO produtoInput) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        
        Produto produto = disassembler.toDomainObject(produtoInput);
        produto.setRestaurante(restaurante);
        
        produto = produtoService.salvar(produto);
        
        return assembler.toModel(produto);
    }
    
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
	@PutMapping(path = "/{produtoId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
            @RequestBody @Valid ProdutoInputDTO produtoInput) {
        Produto produtoAtual = produtoService.buscarOuFalhar(restauranteId, produtoId);
        
        disassembler.copyToDomainObject(produtoInput, produtoAtual);
        
        produtoAtual = produtoService.salvar(produtoAtual);
        
        return assembler.toModel(produtoAtual);
    }   

}
