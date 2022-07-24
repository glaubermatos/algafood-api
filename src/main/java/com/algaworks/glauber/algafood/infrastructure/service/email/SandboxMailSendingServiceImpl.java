package com.algaworks.glauber.algafood.infrastructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SandboxMailSendingServiceImpl extends SmtpEmailSendingServiceImpl {
	
	// Separei a criação de MimeMessage em um método na classe pai (criarMimeMessage),
    // para possibilitar a sobrescrita desse método aqui
	@Override
	protected MimeMessage createMimeMessage(Message message) throws MessagingException {
			
		//monta o objeto mimeMessage chamando o método da classe pai
		MimeMessage mimeMessage = super.createMimeMessage(message);
			
		//para depois customizar o email do destinatário como definido no application.properties
		//em algafood.mail.sandbox.addressee -> destinatario
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setTo(mailProperties.getSandbox().getAddressee());
		
		return mimeMessage;
			
	}

}
