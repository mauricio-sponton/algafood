package com.br.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.algafood.api.assembler.RestauranteDTOAssembler;
import com.br.algafood.api.assembler.RestauranteInputDTODisassembler;
import com.br.algafood.api.model.RestauranteDTO;
import com.br.algafood.api.model.input.RestauranteInputDTO;
import com.br.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.br.algafood.domain.exception.NegocioException;
import com.br.algafood.domain.model.Restaurante;
import com.br.algafood.domain.repository.RestauranteRepository;
import com.br.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private RestauranteDTOAssembler restauranteDTOAssembler;
	
	@Autowired
	private RestauranteInputDTODisassembler restauranteInputDTODisassembler;
	
	@GetMapping
	public List<RestauranteDTO> listar() {
		return restauranteDTOAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
		return  restauranteDTOAssembler.toModel(restaurante);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInputDTO restauranteInput) {
		try {
			Restaurante restaurante = restauranteInputDTODisassembler.toDomainObject(restauranteInput);
			return restauranteDTOAssembler.toModel(cadastroRestaurante.salvar(restaurante));
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId,
			@RequestBody @Valid RestauranteInputDTO restauranteInput) {
		try {
			//Restaurante restaurante = restauranteInputDTODisassembler.toDomainObject(restauranteInput);
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
			
			restauranteInputDTODisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
			
			//BeanUtils.copyProperties(restaurante, restauranteAtual, 
				//	"id", "formasPagamento", "endereco", "dataCadastro", "produtos");

			return restauranteDTOAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
//	@PatchMapping("/{restauranteId}")
//	public Restaurante atualizarParcial(@PathVariable Long restauranteId,
//			@RequestBody Map<String, Object> campos, HttpServletRequest request) {
//		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
//		
//		merge(campos, restauranteAtual, request);
//		
//		return atualizar(restauranteId, restauranteAtual);
//	}

//	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino,
//			HttpServletRequest request) {
//		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//		
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
//			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
//			
//			Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
//			
//			dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//				field.setAccessible(true);
//				
//				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//				
//				ReflectionUtils.setField(field, restauranteDestino, novoValor);
//			});
//		} catch (IllegalArgumentException e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//		}
//	}


}
