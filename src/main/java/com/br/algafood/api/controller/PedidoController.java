package com.br.algafood.api.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.algafood.api.assembler.PedidoDTOAssembler;
import com.br.algafood.api.assembler.PedidoInputDTODisassembler;
import com.br.algafood.api.assembler.PedidoResumoDTOAssembler;
import com.br.algafood.api.model.PedidoDTO;
import com.br.algafood.api.model.PedidoResumoDTO;
import com.br.algafood.api.model.input.PedidoInputDTO;
import com.br.algafood.api.openapi.controller.PedidoControllerOpenApi;
import com.br.algafood.core.data.PageWrapper;
import com.br.algafood.core.data.PageableTranslator;
import com.br.algafood.core.security.AlgaSecurity;
import com.br.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.br.algafood.domain.exception.NegocioException;
import com.br.algafood.domain.filter.PedidoFilter;
import com.br.algafood.domain.model.Pedido;
import com.br.algafood.domain.model.Usuario;
import com.br.algafood.domain.repository.PedidoRepository;
import com.br.algafood.domain.service.PedidoService;
import com.br.algafood.infrastructure.repository.specification.PedidoSpecification;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/pedidos")
public class PedidoController implements PedidoControllerOpenApi {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PedidoDTOAssembler pedidoAssembler;
	
	@Autowired
	private PedidoResumoDTOAssembler pedidoResumoAssembler;
	
	@Autowired
	private PedidoInputDTODisassembler pedidoInputDisassembler;
	
	@Autowired
	private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;
	
	@Autowired
	private AlgaSecurity algaSecurity;
	
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, 
	        @PageableDefault(size = 10) Pageable pageable) {
	    Pageable pageableTraduzido = traduzirPageable(pageable);
	    
	    Page<Pedido> pedidosPage = pedidoRepository.findAll(
	            PedidoSpecification.usandoFiltro(filtro), pageableTraduzido);
	    
	    pedidosPage = new PageWrapper<>(pedidosPage, pageable);
	    
	    return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoAssembler);
	}
	
	@Override
	@GetMapping(path = "/projecao", produces = MediaType.APPLICATION_JSON_VALUE)
	public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<PedidoResumoDTO> pedidosModel = pedidoResumoAssembler.toCollectionModel(pedidos);  
		
		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
		
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
		
		if (StringUtils.isNotBlank(campos)) {
			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
		}
		
		pedidosWrapper.setFilters(filterProvider);
		
		return pedidosWrapper;
	}
	
	@Override
	@GetMapping(path = "/{codigoPedido}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PedidoDTO buscar(@PathVariable String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		return pedidoAssembler.toModel(pedido);
	}
	
	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@Valid @RequestBody PedidoInputDTO pedidoInput) {
	    try {
	        Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

	        novoPedido.setCliente(new Usuario());
	        novoPedido.getCliente().setId(algaSecurity.getUsuarioId());

	        novoPedido = pedidoService.emitir(novoPedido);

	        return pedidoAssembler.toModel(novoPedido);
	    } catch (EntidadeNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}
	
	private Pageable traduzirPageable(Pageable apiPageable) {
		var mapeamento = Map.of(
				"codigo", "codigo",
				"restaurante.nome", "restaurante.nome",
				//"nomeCliente", "cliente.nome",
				"cliente.nome", "cliente.nome",
				"valorTotal", "valorTotal"
		);
		
		return PageableTranslator.translate(apiPageable, mapeamento);
	}
	
	
}