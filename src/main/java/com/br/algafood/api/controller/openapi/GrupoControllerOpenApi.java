package com.br.algafood.api.controller.openapi;

import java.util.List;

import com.br.algafood.api.exception.Problema;
import com.br.algafood.api.model.GrupoDTO;
import com.br.algafood.api.model.input.GrupoInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Lista os grupos")
	List<GrupoDTO> listar();

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID do grupo inválido", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	GrupoDTO buscar(@ApiParam(value = "ID de um grupo") Long grupoId);

	@ApiOperation("Cadastra um grupo")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Grupo cadastrado") })
	GrupoDTO adicionar(GrupoInputDTO grupoInput);

	@ApiOperation("Atualiza um grupo por ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Grupo Atualizado"),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	GrupoDTO atualizar(@ApiParam(value = "ID de um grupo") Long grupoId, GrupoInputDTO grupoInput);

	@ApiOperation("Exclui um grupo por ID")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Grupo excluído"),
			@ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void remover(@ApiParam(value = "ID de um grupo") Long grupoId);

}