package com.br.algafood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.br.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.br.algafood.domain.exception.EntidadeEmUsoException;
import com.br.algafood.domain.model.Cozinha;
import com.br.algafood.domain.service.CadastroCozinhaService;

@SpringBootTest
class CadastroCozinhaIntegrationTests {

	@Autowired
	private CadastroCozinhaService service;

	@Test
	public void testarCadastroCozinhaComSucesso() {

		// Cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome("Chinesa");

		// Ação
		novaCozinha = service.salvar(novaCozinha);

		// Validação
		assertThat(novaCozinha).isNotNull();
		assertThat(novaCozinha.getId()).isNotNull();
	}

	@Test
	public void testarCadastroCozinhaSemNome() {
		// Cenário
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		// Ação
		ConstraintViolationException erroEsperado = Assertions.assertThrows(ConstraintViolationException.class, () -> {
			service.salvar(novaCozinha);
		});

		// Validação
		assertThat(erroEsperado).isNotNull();

	}
	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso() {
		EntidadeEmUsoException erroEsperado = Assertions.assertThrows(EntidadeEmUsoException.class, () -> {
			service.excluir(1L);
		});
		
		assertThat(erroEsperado).isNotNull();
		
	}
	
	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente() {
		CozinhaNaoEncontradaException erroEsperado = Assertions.assertThrows(CozinhaNaoEncontradaException.class, () -> {
			service.excluir(100L);
		});
		
		assertThat(erroEsperado).isNotNull();
	}

}
