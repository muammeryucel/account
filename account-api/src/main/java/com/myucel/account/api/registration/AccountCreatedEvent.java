package com.myucel.account.api.registration;

import java.math.BigDecimal;

import com.myucel.account.api.AccountEvent;

public class AccountCreatedEvent implements AccountEvent {

	private final String accountId;
	private final String phoneNumber;
	private final BigDecimal initialBalance;

	public AccountCreatedEvent(String accountId, String phoneNumber, BigDecimal initialBalance) {
		super();
		this.accountId = accountId;
		this.phoneNumber = phoneNumber;
		this.initialBalance = initialBalance;
	}

	public String getAccountId() {
		return accountId;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public BigDecimal getInitialBalance() {
		return initialBalance;
	}
}
