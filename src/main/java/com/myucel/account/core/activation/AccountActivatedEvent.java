package com.myucel.account.core.activation;

public class AccountActivatedEvent {

	private final String accountId;

	public AccountActivatedEvent(String accountId) {
		super();
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}
}
