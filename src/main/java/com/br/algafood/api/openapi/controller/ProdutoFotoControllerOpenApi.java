package com.br.algafood.api.openapi.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import com.br.algafood.api.exception.Problema;
import com.br.algafood.api.model.FotoProdutoDTO;
import com.br.algafood.api.model.input.ProdutoFotoInputDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Api(tags = "Produtos")
public interface ProdutoFotoControllerOpenApi {

	@ApiOperation("Atualiza a foto de um produto")
	@ApiResponses({ @ApiResponse(responseCode = "200", description = "Foto do produto atualizada"),
			@ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	FotoProdutoDTO atualizarFoto(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId,
			ProdutoFotoInputDTO produtoFotoInputDTO, @ApiParam(value = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)", required = true) MultipartFile arquivo) throws IOException;

	@ApiOperation(value = "Busca a foto do produto de um restaurante", produces = "application/json, image/jpeg, image/png")
	@ApiResponses({ @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválidos"),
			@ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	FotoProdutoDTO buscar(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId, @ApiParam(value = "ID do pedido", example = "1", required = true) Long produtoId);

	@ApiOperation(value = "Busca a foto do produto de um restaurante", hidden = true)
	ResponseEntity<?> servirFoto(Long restauranteId, Long produtoId, String acceptHeader)
			throws HttpMediaTypeNotAcceptableException;

	@ApiOperation(value = "Exclui a foto do produto de um restaurante", produces = "application/json, image/jpeg, image/png")
	@ApiResponses({ @ApiResponse(responseCode = "204", description = "Foto do produto excluida"), @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválidos"),
			@ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = @Content(schema = @Schema(implementation = Problema.class))) })
	void excluir(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId, @ApiParam(value = "ID do pedido", example = "1", required = true) Long produtoId);

}