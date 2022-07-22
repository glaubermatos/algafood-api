package com.algaworks.glauber.algafood.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algaworks.glauber.algafood.api.exceptionhandler.problem.ApiProblemDetailBuilder;
import com.algaworks.glauber.algafood.api.exceptionhandler.problem.FieldErrorBuilder;
import com.algaworks.glauber.algafood.api.exceptionhandler.problem.ProblemType;
import com.algaworks.glauber.algafood.domain.exception.BusinessException;
import com.algaworks.glauber.algafood.domain.exception.EntityInUseException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {  
	
	private static final String MSG_GENERIC_ERROR = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

	@Autowired
	private MessageSource messageSource;
	
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		return ResponseEntity.status(status).headers(headers).build();
	}
	
	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		
		return handleExceptionInternal(ex, headers, status, request, ex.getBindingResult());
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		return handleExceptionInternal(ex, headers, status, request, ex.getBindingResult());
	}

	private ResponseEntity<Object> handleExceptionInternal(Exception ex, HttpHeaders headers,
			HttpStatus status, WebRequest request, BindingResult bindResult) {
		var problemType = ProblemType.INVALID_DATA;
		
		var fieldsError = bindResult.getFieldErrors().stream()
				.map((fieldErro) -> {
					String message = messageSource.getMessage(fieldErro, LocaleContextHolder.getLocale());
					
					return new FieldErrorBuilder()
							.name(fieldErro.getField())
							.userMessage(message)
							.build();
				})
				.collect(Collectors.toList());
		
		var hasManyFieldsWithError = bindResult.getFieldErrorCount() > 1;
		
		var detail = String.format("(%d) campo%s est%s inválido%s. Preencha corretamente e tente novamente", 
				bindResult.getErrorCount(),
				hasManyFieldsWithError ? "s" : "",
				hasManyFieldsWithError ? "ão" : "á",
				hasManyFieldsWithError ? "s" : "");
		
		var apiProblemDetail = createApiProblemDetailBuilder(status, problemType, detail)
				.userMessage(detail)
				.fields(fieldsError)
				.build();
		
		return handleExceptionInternal(ex, apiProblemDetail, headers, status, request);
	}
	
	@ExceptionHandler(Exception.class) 
	public ResponseEntity<Object> handleUncaught(Throwable ex, WebRequest request) {
		
		var status = HttpStatus.INTERNAL_SERVER_ERROR;
		var problemType = ProblemType.SYSTEM_ERROR;
		String detail = MSG_GENERIC_ERROR;
		
		// Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
	    // fazendo logging) para mostrar a stacktrace no console
	    // Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
	    // para você durante, especialmente na fase de desenvolvimento
	    ex.printStackTrace();
		
		var apiProblemDetail = createApiProblemDetailBuilder(status, problemType, detail)
				.userMessage(MSG_GENERIC_ERROR)
				.build();
		
		return handleExceptionInternal((Exception) ex, apiProblemDetail, new HttpHeaders(), status, request);
	}
	
	@Override 
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {		

		var problemType = ProblemType.RESOURCE_NOT_FOUND;
		String datail = String.format("O recurso '%s', que você tentou acessar, é inexistente.",
				ex.getRequestURL());
		
		var apiProblemDetail = createApiProblemDetailBuilder(status, problemType, datail)
				.userMessage(MSG_GENERIC_ERROR)
				.build();
		
		return handleExceptionInternal(ex, apiProblemDetail, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatchException((MethodArgumentTypeMismatchException) ex, headers,
					status, request);
		}
		
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var problemType = ProblemType.REQUEST_URL_INCORRECT;
		String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', que é um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'",
				ex.getName(),
				ex.getValue(),
				ex.getRequiredType().getSimpleName());
		
		var apiProblemDetail = createApiProblemDetailBuilder(status, problemType, detail)
				.userMessage(MSG_GENERIC_ERROR)
				.build();
		
		return handleExceptionInternal(ex, apiProblemDetail, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);		
		
		if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
			
		} else if (rootCause instanceof PropertyBindingException ) {
			return handlePropertyBindingException((PropertyBindingException)rootCause, headers, status, request);
		}
		
		var problemType = ProblemType.REQUEST_BODY_NOT_READABLE;
		String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
		var apiProblemDetail = createApiProblemDetailBuilder(status, problemType, detail)
				.userMessage(MSG_GENERIC_ERROR)
				.build();
		
		return handleExceptionInternal(ex, apiProblemDetail, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		var problemType = ProblemType.REQUEST_BODY_NOT_READABLE;
		
		var path = joinPathFieldName(ex.getPath());
		
		String detail = String .format("A propriedade '%s' é inválida. Corrija ou remova essa propriedade e tente novamente.", 
				path);
		
		var apiProblemDetail = createApiProblemDetailBuilder(status, problemType, detail)
				.userMessage(MSG_GENERIC_ERROR) 
				.build();
		
		return handleExceptionInternal(ex, apiProblemDetail, new HttpHeaders(), status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		var problemType = ProblemType.REQUEST_BODY_NOT_READABLE;
				
		var path = joinPathFieldName(ex.getPath());
		
		String detail = String .format("A propriedade '%s' recebeu o valor '%s' "
				+ "que é de um tipo inválido. Corrija e informe um valor compatível com tipo o '%s'", 
				path,
				ex.getValue(),
				ex.getTargetType().getSimpleName());
		
		var apiProblemDetail = createApiProblemDetailBuilder(status, problemType, detail)
				.userMessage(MSG_GENERIC_ERROR)
				.build();

		return handleExceptionInternal(ex, apiProblemDetail, new HttpHeaders(), status, request);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {	
		
		var status = HttpStatus.NOT_FOUND;
		var problemType = ProblemType.RESOURCE_NOT_FOUND;
		String detail = ex.getMessage();
		
		var apiProblemDetail = createApiProblemDetailBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, apiProblemDetail, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntityInUseException.class)
	public ResponseEntity<?> handleEntityInUseException(EntityInUseException ex, WebRequest request) {	
		
		var status = HttpStatus.CONFLICT;
		var problemType = ProblemType.ENTITY_IN_USE;
		String detail = ex.getMessage();
		
		var apiProblemDetail = createApiProblemDetailBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, apiProblemDetail, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {			

		var status = HttpStatus.BAD_REQUEST;
		var problemType = ProblemType.BUSINESS_ERROR;
		String detail = ex.getMessage();
		
		var apiProblemDetail = createApiProblemDetailBuilder(status, problemType, detail)
				.userMessage(detail)
				.build();
		
		return handleExceptionInternal(ex, apiProblemDetail, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {				
		
		if (body == null) {
			body = new ApiProblemDetailBuilder()
					.status(status.value())
					.title(status.getReasonPhrase())
					.userMessage(MSG_GENERIC_ERROR)
					.timestamp(OffsetDateTime.now())
					.build();
		} else if (body instanceof String) {
			body = new ApiProblemDetailBuilder()
					.status(status.value())
					.title((String) body)
					.userMessage(MSG_GENERIC_ERROR)
					.timestamp(OffsetDateTime.now())
					.build();
		}		
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	private String joinPathFieldName(List<Reference> references) {
		return references.stream()
				.map((ref) -> ref.getFieldName())
				.collect(Collectors.joining("."));
	}

	private ApiProblemDetailBuilder createApiProblemDetailBuilder(HttpStatus httpStatus, ProblemType problemType, String detail) {
		return new ApiProblemDetailBuilder()
				.timestamp(OffsetDateTime.now())
				.status(httpStatus.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.detail(detail);
	}
}
