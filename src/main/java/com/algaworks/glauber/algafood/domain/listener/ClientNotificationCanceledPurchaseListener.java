package com.algaworks.glauber.algafood.domain.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.algaworks.glauber.algafood.domain.event.CanceledPurchaseEvent;
import com.algaworks.glauber.algafood.domain.model.Purchase;
import com.algaworks.glauber.algafood.domain.service.MailSendingService;
import com.algaworks.glauber.algafood.domain.service.MailSendingService.Message;

@Component
public class ClientNotificationCanceledPurchaseListener {
	
	@Autowired
	private MailSendingService mailSendingService;

	@TransactionalEventListener
	public void whenCanceledPurchase(CanceledPurchaseEvent event) {
		Purchase purchase = event.getPurchase();
		
		String clientEmail = purchase.getClient().getEmail();
		String subject = purchase.getRestaurant().getName() + " - Pedido cancelado." ;
		
		Set<String> recipients = new HashSet<>();
		recipients.add(clientEmail); 
		String body = "canceled-purchase.html";
		
		//Objeto para montar o template do email
		Map<String, Object> purchaseObjectModel = new HashMap<>();
		purchaseObjectModel.put("purchase", purchase);
		
		Message message = new Message(recipients, subject, body, purchaseObjectModel);
		
		mailSendingService.send(message);
	}
}
