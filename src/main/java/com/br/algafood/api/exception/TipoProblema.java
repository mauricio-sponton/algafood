package com.br.algafood.api.exception;

import lombok.Getter;

@Getter
public enum TipoProblema {
	
	DADOS_INVALIDOS("Dados inválidos", "/dados-invalidos"),
	ERRO_DE_SISTEMA("Erro de sistema", "/erro-de-sistema"),
	PARAMETRO_INVALIDO("Parâmetro inválido", "/parametro-invalido"),
	MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensível", "/mensagem-incompreensivel"),
	RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "/recurso-nao-encontrado"), 
	ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
	ERRO_NEGOCIO("Violação de regra de negócio", "/erro-negocio");
	
	private String title;
	private String uri;
	
	private TipoProblema(String title, String path) {
		this.title = title;
		this.uri = "https://algafood.com.br/" + path;
	}
	
	
}
