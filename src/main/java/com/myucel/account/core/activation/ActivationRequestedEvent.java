package com.myucel.account.core.activation;

import java.math.BigDecimal;

public class ActivationRequestedEvent {

	private final String phoneNumber;
	private final String activationCode;

	public ActivationRequestedEvent(String phoneNumber, String activationCode) {
		super();
		this.phoneNumber = phoneNumber;
		this.activationCode = activationCode;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getActivationCode() {
		return activationCode;
	}
}
