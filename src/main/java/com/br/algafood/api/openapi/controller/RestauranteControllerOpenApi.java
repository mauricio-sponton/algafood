package com.br.algafood.api.openapi.controller;

import java.util.List;

import com.br.algafood.api.exception.Problema;
import com.br.algafood.api.model.RestauranteDTO;
import com.br.algafood.api.model.input.RestauranteInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@ApiOperation("Lista os restaurantes")
	List<RestauranteDTO> listar();

	@ApiOperation("Busca um restaurante por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	RestauranteDTO buscar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId);

	@ApiOperation("Cadastra um restaurante")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Grupo cadastrado") })
	RestauranteDTO adicionar(RestauranteInputDTO restauranteInput);

	@ApiOperation("Atualiza um restaurante por ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Restaurante Atualizado"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	RestauranteDTO atualizar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId, RestauranteInputDTO restauranteInput);

	
	@ApiOperation("Ativa um restaurante por ID")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void ativar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId);

	@ApiOperation("Inativa um restaurante por ID")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void inativar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId);

	@ApiOperation("Ativa múltiplos restaurantes")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso")})
	void ativarMultiplos(@ApiParam(value = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@ApiOperation("Inativa múltiplos restaurantes")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante inativados com sucesso")})
	void inativarMultiplos(@ApiParam(value = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@ApiOperation("Abre um restaurante por ID")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void abrir(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId);

	@ApiOperation("Fecha um restaurante por ID")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void fechar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId);

}