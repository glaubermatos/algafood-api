package com.algaworks.glauber.algafood.core.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algaworks.glauber.algafood.domain.service.MailSendingService;
import com.algaworks.glauber.algafood.infrastructure.service.email.FakeMailSendingServiceImpl;
import com.algaworks.glauber.algafood.infrastructure.service.email.SandboxMailSendingServiceImpl;
import com.algaworks.glauber.algafood.infrastructure.service.email.SmtpEmailSendingServiceImpl;

@Configuration
public class MailConfig {

	@Autowired
	private MailProperties mailProperties;
	
	@Bean
	public MailSendingService mailSendingService() {
		switch (mailProperties.getImpl()) {
			case FAKE:
				return new FakeMailSendingServiceImpl();
				
			case SMTP:
				return new SmtpEmailSendingServiceImpl();
				
			case SANDBOX:
				return new SandboxMailSendingServiceImpl();
				
			default:
				return null;
		}
	}
}
