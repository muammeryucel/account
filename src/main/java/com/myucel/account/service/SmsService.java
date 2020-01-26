package com.myucel.account.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {

	public void sendActivationCode(String phoneNumber, String activationCode) {
		System.out.println("***************************************************");
		System.out.println("SMS composed and sent to " + phoneNumber);
		System.out.println("Activation Code: " + activationCode);
		System.out.println("***************************************************");
	}
}
