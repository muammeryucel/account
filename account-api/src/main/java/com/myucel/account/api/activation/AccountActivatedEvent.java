package com.myucel.account.api.activation;

import com.myucel.account.api.AccountEvent;

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
