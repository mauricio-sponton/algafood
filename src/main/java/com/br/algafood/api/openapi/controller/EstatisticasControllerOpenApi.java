package com.br.algafood.api.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.br.algafood.api.controller.EstatisticasController.EstatisticasDTO;
import com.br.algafood.domain.filter.VendaDiariaFilter;
import com.br.algafood.domain.model.dto.VendaDiaria;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Estatísticas")
public interface EstatisticasControllerOpenApi {

	 @ApiOperation("Consulta estatísticas de vendas diárias")
	    @ApiImplicitParams({
	        @ApiImplicitParam(name = "restauranteId", value = "ID do restaurante", 
	                example = "1", dataType = "int"),
	        @ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora inicial da criação do pedido",
	            example = "2019-12-01T00:00:00Z", dataType = "date-time"),
	        @ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora final da criação do pedido",
	            example = "2019-12-02T23:59:59Z", dataType = "date-time")
	    })
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);

	ResponseEntity<byte[]> consultarVendasDiariasPDF(VendaDiariaFilter filtro, String timeOffset);

	@ApiOperation(value = "Estatísticas", hidden = true)
	EstatisticasDTO estatisticas();

}