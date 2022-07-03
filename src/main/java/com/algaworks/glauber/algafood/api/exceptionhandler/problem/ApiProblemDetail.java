package com.algaworks.glauber.algafood.api.exceptionhandler.problem;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"status", "timestamp", "type","title", "detail", "userMessage" })
@JsonInclude(value = Include.NON_NULL)
public class ApiProblemDetail {
	
	protected ApiProblemDetail() {}

	protected Integer status;
	protected String type;
	protected String title;
	protected String detail;
	protected LocalDateTime timestamp;	
	protected String userMessage;
	protected List<Field> fields;;
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}


	public static class Field {
		
		protected Field() {}
		
		protected String name;
		protected String userMessage;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUserMessage() {
			return userMessage;
		}
		public void setUserMessage(String message) {
			this.userMessage = message;
		}
		
		
	}
}
