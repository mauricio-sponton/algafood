package com.br.algafood.api.controller.openapi;

import java.util.List;

import com.br.algafood.api.exception.Problema;
import com.br.algafood.api.model.CidadeDTO;
import com.br.algafood.api.model.input.CidadeInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista as cidades")
	List<CidadeDTO> listar();

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID da cidade inválido", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(implementation = Problema.class))) })
	CidadeDTO buscar(@ApiParam(value = "ID de uma cidade") Long cidadeId);

	@ApiOperation("Cadastra uma cidade")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Cidade cadastrada") })
	CidadeDTO adicionar(CidadeInputDTO cidadeInput);

	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Cidade Atualizada"),
			@ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(implementation = Problema.class))) })
	CidadeDTO atualizar(@ApiParam(value = "ID de uma cidade") Long cidadeId, CidadeInputDTO cidadeInput);

	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Cidade excluída"),
			@ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void remover(Long cidadeId);

}