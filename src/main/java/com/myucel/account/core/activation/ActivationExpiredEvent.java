package com.myucel.account.core.activation;

import com.myucel.account.core.AccountEvent;

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
