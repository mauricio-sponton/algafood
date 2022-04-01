package com.br.algafood.api.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import com.br.algafood.core.data.PageableTranslator;
import com.br.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.br.algafood.domain.exception.NegocioException;
import com.br.algafood.domain.model.Pedido;
import com.br.algafood.domain.model.Usuario;
import com.br.algafood.domain.repository.PedidoRepository;
import com.br.algafood.domain.repository.filter.PedidoFilter;
import com.br.algafood.domain.service.PedidoService;
import com.br.algafood.infrastructure.repository.specification.PedidoSpecification;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

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
	
	@GetMapping
	public Page<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable) {
		pageable = traduzirPageable(pageable);
		Page<Pedido> pedidos = pedidoRepository.findAll(PedidoSpecification.usandoFiltro(filtro), pageable);
		List<PedidoResumoDTO> pedidosDTO = pedidoResumoAssembler.toCollectionModel(pedidos.getContent());
		Page<PedidoResumoDTO> pedidosPage = new PageImpl<PedidoResumoDTO>(pedidosDTO, pageable, pedidos.getTotalElements());
		return pedidosPage;
	}
	
	@GetMapping("/projecao")
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
	
	@GetMapping("/{codigoPedido}")
	public PedidoDTO buscar(@PathVariable String codigoPedido) {
		Pedido pedido = pedidoService.buscarOuFalhar(codigoPedido);
		return pedidoAssembler.toModel(pedido);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO adicionar(@Valid @RequestBody PedidoInputDTO pedidoInput) {
	    try {
	        Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

	        // TODO pegar usuário autenticado
	        novoPedido.setCliente(new Usuario());
	        novoPedido.getCliente().setId(1L);

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