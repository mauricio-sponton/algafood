package com.br.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.algafood.api.assembler.PedidoDTOAssembler;
import com.br.algafood.api.model.PedidoDTO;
import com.br.algafood.domain.model.Pedido;
import com.br.algafood.domain.repository.PedidoRepository;
import com.br.algafood.domain.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PedidoDTOAssembler assembler;
	
	@GetMapping
	public List<PedidoDTO> listar() {
		return assembler.toCollectionModel(pedidoRepository.findAll());
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoDTO buscar(@PathVariable Long pedidoId) {
		Pedido pedido = pedidoService.buscarOuFalhar(pedidoId);
		return assembler.toModel(pedido);
	}
	
	
}