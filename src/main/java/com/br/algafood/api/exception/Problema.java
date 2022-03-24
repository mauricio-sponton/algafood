package com.br.algafood.api.exception;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problema {
	
	private Integer status;
	private String type;
	private String title;
	private String detail;

	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss'Z'")
	private OffsetDateTime dataHora;
	private String userMessage;
	
	private List<Field> fields;
	
	@Getter
	@Builder
	public static class Field {
		
		private String name;
		private String userMessage;
	}
	
}