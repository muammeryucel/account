package com.myucel.account.core.activation;

import com.myucel.account.core.AccountEvent;

public class AccountActivatedEvent implements AccountEvent {

	private final String accountId;

	public AccountActivatedEvent(String accountId) {
		super();
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}
}
