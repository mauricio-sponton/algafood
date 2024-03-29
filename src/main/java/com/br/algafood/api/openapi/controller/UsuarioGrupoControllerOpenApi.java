package com.br.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.br.algafood.api.exception.Problema;
import com.br.algafood.api.model.GrupoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioGrupoControllerOpenApi {

	@ApiOperation("Lista grupos associados ao usuário")
	@ApiResponses({@ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = @Content(schema = @Schema(implementation = Problema.class))),
		@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	CollectionModel<GrupoDTO> listar(@ApiParam(value = "ID de um usuário", required = true) Long usuarioId);

	@ApiOperation("Desassociação de grupo com o usuário")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Grupo ou usuário não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	ResponseEntity<Void> desassociar(@ApiParam(value = "ID de um usuário", required = true) Long usuarioId, @ApiParam(value = "ID de um grupo", required = true) Long grupoId);

	@ApiOperation("Associação de grupo com usuário")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Associação realizada com sucesso", content = @Content(schema = @Schema(implementation = Problema.class))),
		@ApiResponse(responseCode = "404", description = "Grupo ou usuário não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	ResponseEntity<Void> associar(@ApiParam(value = "ID de um usuário", required = true) Long usuarioId, @ApiParam(value = "ID de um grupo", required = true) Long grupoId);

}