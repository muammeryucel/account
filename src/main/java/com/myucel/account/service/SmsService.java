package com.myucel.account.service;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import com.myucel.account.core.activation.ActivationRequestedEvent;

@Service
public class SmsService {

	@EventHandler
	public void on(ActivationRequestedEvent event) {
		String phoneNumber = event.getPhoneNumber();
		String activationCode = event.getActivationCode();

		sendActivationCode(phoneNumber, activationCode);
	}

	public void sendActivationCode(String phoneNumber, String activationCode) {
		System.out.println("***************************************************");
		System.out.println("SMS composed and sent to " + phoneNumber);
		System.out.println("Activation Code: " + activationCode);
		System.out.println("***************************************************");
	}
}
