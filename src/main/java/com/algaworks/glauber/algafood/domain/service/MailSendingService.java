package com.algaworks.glauber.algafood.domain.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface MailSendingService {

	void send(Message message);
	
	public class Message {
		
		private Set<String> recipients = new HashSet<>();
		private String subject;
		private String body;
		private Map<String, Object> variables;
		
		public Message(Set<String> recipients, String subject, String body, Map<String, Object> variables) {
			this.recipients = recipients;
			this.subject = subject;
			this.body = body;
			this.variables = variables;
		}
		
		public Set<String> getRecipients() {
			return recipients;
		}
		public void setRecipients(Set<String> recipients) {
			this.recipients = recipients;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getBody() {
			return body;
		}
		public void setBody(String body) {
			this.body = body;
		}
		public Map<String, Object> getVariables() {
			return variables;
		}
		public void setVariables(Map<String, Object> variables) {
			this.variables = variables;
		}
		
	}
}
