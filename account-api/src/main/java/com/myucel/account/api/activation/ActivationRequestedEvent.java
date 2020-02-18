package com.myucel.account.api.activation;

import com.myucel.account.api.ExternalEvent;

public class ActivationRequestedEvent implements ExternalEvent {

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
