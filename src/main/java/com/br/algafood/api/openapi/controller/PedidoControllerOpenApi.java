package com.br.algafood.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.converter.json.MappingJacksonValue;

import com.br.algafood.api.exception.Problema;
import com.br.algafood.api.model.PedidoDTO;
import com.br.algafood.api.model.PedidoResumoDTO;
import com.br.algafood.api.model.input.PedidoInputDTO;
import com.br.algafood.domain.filter.PedidoFilter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Pedidos")
public interface PedidoControllerOpenApi {

	@ApiOperation("Pesquisa os pedidos")
	PagedModel<PedidoResumoDTO> pesquisar(PedidoFilter filtro, Pageable pageable);

	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula", name = "campos", paramType = "query", type = "string") })
	MappingJacksonValue listar(String campos);

	@ApiOperation("Busca um pedido por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID do pedido inválido", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	PedidoDTO buscar(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", 
			required = true) String codigoPedido);

	@ApiOperation("Cadastra um pedido")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Pedido cadastrado") })
	PedidoDTO adicionar(PedidoInputDTO pedidoInput);

}