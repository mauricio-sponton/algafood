package com.br.algafood.api.openapi.controller;

import java.util.List;

import com.br.algafood.api.exception.Problema;
import com.br.algafood.api.model.UsuarioDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenApi {

	@ApiOperation("Lista usuários responsáveis associados ao restaurante")
	@ApiResponses({@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	List<UsuarioDTO> listar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId);

	@ApiOperation("Desassociação de restaurante com usuário responsável")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void desassociar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId, @ApiParam(value = "ID de um usuario", required = true) Long usuarioId);

	@ApiOperation("Associação de restaurante com usuário responsável")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Associação realizada com sucesso", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void associar(@ApiParam(value = "ID de um restaurante", required = true) Long restauranteId, @ApiParam(value = "ID de um usuário", required = true) Long usuarioId);

}