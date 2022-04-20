package com.br.algafood.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.br.algafood.api.exception.Problema;
import com.br.algafood.api.model.FormaPagamentoDTO;
import com.br.algafood.api.model.input.FormaPagamentoInputDTO;
import com.br.algafood.api.openapi.model.FormasPagamentoModelOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

	@ApiOperation(value = "Lista as formas de pagamento", response = FormasPagamentoModelOpenApi.class)
	public ResponseEntity<CollectionModel<FormaPagamentoDTO>> listar(ServletWebRequest request);

	@ApiOperation("Busca uma forma de pagamento por ID")
	@ApiResponses({
			@ApiResponse(responseCode = "400", description = "ID da forma de pagamento inválida", content = @Content(schema = @Schema(implementation = Problema.class))),
			@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(implementation = Problema.class))) })
	public ResponseEntity<FormaPagamentoDTO> buscar(
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId,

			ServletWebRequest request);

	@ApiOperation("Cadastra uma forma de pagamento")
	@ApiResponses({ @ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada") })
	public FormaPagamentoDTO adicionar(FormaPagamentoInputDTO formaPagamentoInput);

	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada"),
			@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(implementation = Problema.class))) })
	public FormaPagamentoDTO atualizar(
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId,
			FormaPagamentoInputDTO formaPagamentoInput);

	@ApiOperation("Exclui uma forma de pagamento por ID")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Forma de pagamento excluída"),
			@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = @Content(schema = @Schema(implementation = Problema.class))) })
	public void remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);
}
