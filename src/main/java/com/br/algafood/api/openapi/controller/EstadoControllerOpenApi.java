package com.br.algafood.api.openapi.controller;

import java.util.List;

import com.br.algafood.api.exception.Problema;
import com.br.algafood.api.model.EstadoDTO;
import com.br.algafood.api.model.input.EstadoInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation("Lista os estados")
	List<EstadoDTO> listar();

	@ApiOperation("Busca um estado por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID do estado inválido", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	EstadoDTO buscar(Long estadoId);

	@ApiOperation("Cadastra um estado")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Estado cadastrado") })
	EstadoDTO adicionar(EstadoInputDTO estadoInput);

	@ApiOperation("Atualiza um estado por ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Estado Atualizado"),
			@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	EstadoDTO atualizar(Long estadoId, EstadoInputDTO estadoInput);

	@ApiOperation("Exclui um estado por ID")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Estado excluído"),
			@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void remover(Long estadoId);

}