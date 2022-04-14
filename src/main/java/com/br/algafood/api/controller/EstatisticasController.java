package com.br.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.openapi.controller.EstatisticasControllerOpenApi;
import com.br.algafood.domain.filter.VendaDiariaFilter;
import com.br.algafood.domain.model.dto.VendaDiaria;
import com.br.algafood.domain.service.VendaQueryService;
import com.br.algafood.domain.service.VendaReportService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {
	
	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@Autowired
	private AlgaLinks algaLinks;

	@Override
	@GetMapping(value = "vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
	}
	
	@Override
	@GetMapping(value = "vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPDF(VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
	
	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public EstatisticasDTO estatisticas() {
	    var estatisticasModel = new EstatisticasDTO();
	    
	    estatisticasModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));
	    
	    return estatisticasModel;
	} 
	
	public static class EstatisticasDTO extends RepresentationModel<EstatisticasDTO> {
	}
}
