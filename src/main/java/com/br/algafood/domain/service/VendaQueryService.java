package com.br.algafood.domain.service;

import java.util.List;

import com.br.algafood.domain.filter.VendaDiariaFilter;
import com.br.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {
	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
