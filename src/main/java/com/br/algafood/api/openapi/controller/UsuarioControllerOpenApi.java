package com.br.algafood.api.openapi.controller;

import java.util.List;

import com.br.algafood.api.exception.Problema;
import com.br.algafood.api.model.UsuarioDTO;
import com.br.algafood.api.model.input.SenhaInputDTO;
import com.br.algafood.api.model.input.UsuarioComSenhaInputDTO;
import com.br.algafood.api.model.input.UsuarioInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

	@ApiOperation("Lista os usuários")
	List<UsuarioDTO> listar();

	@ApiOperation("Busca um usuário por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	UsuarioDTO buscar(@ApiParam(value = "ID de um usuário", required = true) Long usuárioId);

	@ApiOperation("Cadastra um usuário")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Usuário cadastrado") })
	UsuarioDTO adicionar(UsuarioComSenhaInputDTO usuárioInput);

	@ApiOperation("Atualiza um usuário por ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Usuário Atualizado"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	UsuarioDTO atualizar(@ApiParam(value = "ID de um usuário", required = true) Long usuárioId, UsuarioInputDTO usuárioInput);

	@ApiOperation("Exclui um usuário por ID")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Usuário excluído"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void alterarSenha(@ApiParam(value = "ID de um usuário", required = true) Long usuárioId, SenhaInputDTO senha);

}