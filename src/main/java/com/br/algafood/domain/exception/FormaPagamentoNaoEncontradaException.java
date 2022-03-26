package com.br.algafood.domain.exception;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	public FormaPagamentoNaoEncontradaException(Long restauranteId) {
		this(String.format("Não existe uma forma de pagamento cadastrada com o código %d", restauranteId));
	}
	
}