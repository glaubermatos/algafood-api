package com.algaworks.glauber.algafood.core.mail;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@ConfigurationProperties("algafood.mail")
public class MailProperties {

	private ImplType impl = ImplType.FAKE;
	
	@NotNull//o spring validator verifica se a propriedae sender no arquivo application.properties está null
	private String sender;
	private Sandbox sandbox = new Sandbox();
	
	
	public Sandbox getSandbox() {
		return sandbox;
	}
	public void setSandbox(Sandbox sandBox) {
		this.sandbox = sandBox;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public ImplType getImpl() {
		return impl;
	}
	public void setImpl(ImplType impl) {
		this.impl = impl;
	}

	enum ImplType {
		FAKE, SMTP, SANDBOX
	}
	
	public class Sandbox {
		
		private String addressee;

		public String getAddressee() {
			return addressee;
		}
		public void setAddressee(String addressee) {
			this.addressee = addressee;
		}
		
	}
	
}
