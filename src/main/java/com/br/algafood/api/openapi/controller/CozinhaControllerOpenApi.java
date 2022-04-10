package com.br.algafood.api.openapi.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import com.br.algafood.api.exception.Problema;
import com.br.algafood.api.model.CozinhaDTO;
import com.br.algafood.api.model.input.CozinhaInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation("Lista as cozinhas")
	PagedModel<CozinhaDTO> listar(Pageable pageable);

	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID da cozinha inválida", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(implementation = Problema.class))) })
	CozinhaDTO buscar(@ApiParam(value = "ID de uma cozinha", required = true) Long cozinhaId);

	@ApiOperation("Cadastra uma cozinha")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Cozinha cadastrada") })
	CozinhaDTO adicionar(CozinhaInputDTO cozinhaInput);

	@ApiOperation("Atualiza uma cozinha por ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Cozinha Atualizada"),
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(implementation = Problema.class))) })
	CozinhaDTO atualizar(@ApiParam(value = "ID de uma cozinha", required = true) Long cozinhaId, CozinhaInputDTO cozinhaInput);

	@ApiOperation("Exclui uma cozinha por ID")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Cozinha excluída"),
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void remover(@ApiParam(value = "ID de uma cozinha", required = true) Long cozinhaId);

}