package com.br.algafood.api.exception;

import lombok.Getter;

@Getter
public enum TipoProblema {
	
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	PARAMETRO_INVALIDO("Parâmetro inválido", "/parametro-invalido"),
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "/recurso-nao-encontrado"), 
	ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio");
	
	private String title;
	private String uri;
	
	private TipoProblema(String title, String path) {
		this.title = title;
		this.uri = "https://algafood.com.br/" + path;
	}
	
	
}
