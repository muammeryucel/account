package com.myucel.account.core.activation;

public class ActivationExpiredEvent {

	private final String accountId;

	public ActivationExpiredEvent(String accountId) {
		super();
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}
}
