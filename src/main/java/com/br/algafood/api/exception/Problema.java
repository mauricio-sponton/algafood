package com.br.algafood.api.exception;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problema {
	
	@ApiModelProperty(example = "400")
	private Integer status;
	
	@ApiModelProperty(example = "https://algafood.com.br/dados-invalidos")
	private String type;
	
	@ApiModelProperty(example = "Dados inválidos")
	private String title;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto")
	private String detail;

	@ApiModelProperty(example = "2019-12-10T18:09:02Z")
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
	private OffsetDateTime dataHora;
	
	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto")
	private String userMessage;
	
	@ApiModelProperty(value = "Objetos ou campos que geraram o erro")
	private List<Field> fields;
	
	@ApiModel("CampoProblema")
	@Getter
	@Builder
	public static class Field {
		
		@ApiModelProperty(example = "preco")
		private String name;
		
		@ApiModelProperty(example = "O preço é obrigatório")
		private String userMessage;
	}
	
}