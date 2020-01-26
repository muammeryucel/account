package com.myucel.account.core.activation;

import com.myucel.account.core.AccountEvent;

public class ActivationRequestedEvent implements AccountEvent {

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
