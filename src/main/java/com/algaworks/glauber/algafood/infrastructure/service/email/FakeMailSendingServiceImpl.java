package com.algaworks.glauber.algafood.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.glauber.algafood.domain.service.MailSendingService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class FakeMailSendingServiceImpl implements MailSendingService {

	@Autowired
	private Configuration freeMakerConfig;

	@SuppressWarnings("static-access")
	@Override
	public void send(Message message) {
		try {
			Template template = freeMakerConfig.getTemplate(message.getBody());
			
			//ObjectModel para fazer o bind com template de email em html usando o freemaker
			Object objectModel = message.getVariables();
			
			String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, objectModel);
			
			System.out.println(String.format("[FAKE E-MAIL] Para %s\n%s", 
					message.getRecipients().stream()
					.reduce((anterior, atual) -> anterior.join(atual, ", "))
					.orElse(""),
					body
					));
		} catch (Exception e) {
			throw new MailSendingException("Não foi possível processar o template do e-mail", e);
		}
	}

}
