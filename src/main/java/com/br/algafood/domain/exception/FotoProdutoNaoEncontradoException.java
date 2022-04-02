package com.br.algafood.domain.exception;

public class FotoProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FotoProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public FotoProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
		this(String.format("Não existe foto do produto cadastrado com o código %d no restaurante %d", produtoId, restauranteId));
	}
	
}