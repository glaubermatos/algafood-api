package com.algaworks.glauber.algafood.api.exceptionhandler.problem;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;

public class ApiProblemDetailBuilder {

	private ApiProblemDetail instance;
	
	public ApiProblemDetailBuilder() {
		this.instance = new ApiProblemDetail();
	}
	
//	public ApiProblemDetailBuilder timestamp(LocalDateTime timestamp) {
//		this.instance.setTimestamp(timestamp);
//		
//		return this;
//	}
	
	public ApiProblemDetailBuilder status(Integer status) {
		this.instance.setStatus(status);
		
		return this;
	}
	
	public ApiProblemDetailBuilder type(String type) {
		this.instance.setType(type);
		
		return this;
	}
	
	public ApiProblemDetailBuilder title(String title) {
		this.instance.setTitle(title);
		
		return this;
	}
	
	public ApiProblemDetailBuilder detail(String detail) {
		this.instance.setDetail(detail);
		
		return this;
	}
	
	public ApiProblemDetailBuilder userMessage(String userMessage) {
		this.instance.setUserMessage(userMessage);
		
		return this;
	}
	
	public ApiProblemDetailBuilder timestamp(LocalDateTime timestamp) {
		this.instance.setTimestamp(timestamp);
		
		 return this;
	}
	
	public ApiProblemDetail build() {
		if (StringUtils.isBlank(this.instance.getUserMessage())) {
			throw new IllegalStateException("A propriedade ApiProblemDetail.userMessage não pode ser nula. Deve ser preenchida com a mensagem lançadas pelas exceções tratadas");
		}
		
		return this.instance;
	}
}
