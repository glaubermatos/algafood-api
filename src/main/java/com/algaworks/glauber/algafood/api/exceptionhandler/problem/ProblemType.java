package com.algaworks.glauber.algafood.api.exceptionhandler.problem;

public enum ProblemType {

	INVALID_DATA("Dados inválidos", "/invalid-data"),
	SYSTEM_ERROR("Erro de sistema", "/system-erro"),
	REQUEST_URL_INCORRECT("Parâmetros da requisição incorretos", "/request-parameters-incorrect"),
	REQUEST_BODY_NOT_READABLE("Corpo da requisição imcompreensivo", "/request-body-not-readable"),
	RESOURCE_NOT_FOUND("Recurso não encontrado", "/resource-not-found"),
	ENTITY_IN_USE("Entidade em uso", "/entity-in-use"),
	BUSINESS_ERROR("Violação de regra de negócio", "/business-exception");

	private static final String URL = "https://algafood.com.br";
	
	private String title;
	private String uri;
	
	private ProblemType(String title, String path) {
		this.title = title;
		this.uri = URL.concat(path);
	}

	public String getTitle() {
		return title;
	}

	public String getUri() {
		return uri;
	}
	
}
