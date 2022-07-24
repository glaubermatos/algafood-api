package com.algaworks.glauber.algafood.infrastructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.glauber.algafood.core.mail.MailProperties;
import com.algaworks.glauber.algafood.domain.service.MailSendingService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class SmtpEmailSendingServiceImpl implements MailSendingService {
	
	@Autowired
	protected JavaMailSender javaMailSender;
	
	@Autowired
	protected MailProperties mailProperties;
	
	@Autowired
	private Configuration freeMakerConfig;
	
	@Override
	public void send(Message message) {
		try {
			MimeMessage mimeMessage = createMimeMessage(message);			
			
			javaMailSender.send(mimeMessage);
			
		} catch (Exception e) {
			throw new MailSendingException("Não foi possível enviar e-mail", e);
		}
	}

	protected MimeMessage createMimeMessage(Message message) throws MessagingException {
		String body = processTemplate(message);
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setFrom(mailProperties.getSender());
		helper.setTo(message.getRecipients().toArray(new String[0]));
		helper.setSubject(message.getSubject());
		helper.setText(body, true);
		
		return mimeMessage;
	}
	
	protected String processTemplate(Message message ) {
		try {
			Template template = freeMakerConfig.getTemplate(message.getBody());
			
			//ObjectModel para fazer o bind com template de email em html usando o freemaker
			Object objectModel = message.getVariables();
			
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, objectModel);
		} catch (Exception e) {
			throw new MailSendingException("Não foi possível processar o template do e-mail", e);
		}
	}

}
