package com.br.algafood.api.openapi.controller;

import com.br.algafood.api.exception.Problema;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Pedidos")
public interface FluxoPedidoControllerOpenApi {

	@ApiOperation("Confirma um pedido pelo código")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Pedido confirmado com sucesso", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void confirmar(@ApiParam(value = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", 
            required = true) String codigoPedido);

	@ApiOperation("Cancela um pedido pelo código")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void cancelar(@ApiParam(value = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", 
            required = true) String codigoPedido);

	@ApiOperation("Registra entrega de pedido")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Pedido entregue com sucesso", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void entregar(@ApiParam(value = "Código do pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55", 
            required = true) String codigoPedido);

}