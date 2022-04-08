package com.br.algafood.api.openapi.controller;

import java.util.List;

import com.br.algafood.api.exception.Problema;
import com.br.algafood.api.model.ProdutoDTO;
import com.br.algafood.api.model.input.ProdutoInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

	@ApiOperation("Lista produtos de um restaurante")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	List<ProdutoDTO> listar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId, boolean incluirInativos);

	@ApiOperation("Busca um produto de um restaurante")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "produto do restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	ProdutoDTO buscar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId, @ApiParam(value = "ID de um produto", required = true) Long produtoId);

	@ApiOperation("Cadastra um produto de um restaurante")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Produto cadastrado") })
	ProdutoDTO adicionar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId, ProdutoInputDTO produtoInput);

	@ApiOperation("Atualiza um produto de um restaurante")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Produto atualizado"),
			@ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	ProdutoDTO atualizar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId, @ApiParam(value = "ID de um produto", required = true) Long produtoId, ProdutoInputDTO produtoInput);

}