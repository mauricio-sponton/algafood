package com.br.algafood.api.openapi.controller;

import java.util.List;

import com.br.algafood.api.exception.Problema;
import com.br.algafood.api.model.PermissaoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoPermissaoControllerOpenApi {

	@ApiOperation("Lista permissões associadas ao grupo")
	@ApiResponses({@ApiResponse(responseCode = "400", description = "ID do grupo inválido", content = @Content(schema = @Schema(implementation = Problema.class))),
		@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	List<PermissaoDTO> listar(@ApiParam(value = "ID de um grupo", required = true) Long grupoId);

	@ApiOperation("Desassociação de permissão com grupo")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void desassociar(@ApiParam(value = "ID de um grupo", required = true) Long grupoId, @ApiParam(value = "ID de uma permissão", required = true) Long permissaoId);

	@ApiOperation("Associação de permissão com grupo")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Associação realizada com sucesso", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Grupo ou permissão não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void associar(@ApiParam(value = "ID de um grupo", required = true) Long grupoId, @ApiParam(value = "ID de uma permissão", required = true)Long permissaoId);

}