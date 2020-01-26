package com.myucel.account.api.activation;

import com.myucel.account.api.AccountEvent;

public class ActivationExpiredEvent implements AccountEvent {

	private final String accountId;

	public ActivationExpiredEvent(String accountId) {
		super();
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}
}
