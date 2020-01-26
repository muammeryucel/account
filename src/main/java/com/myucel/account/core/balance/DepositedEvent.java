package com.myucel.account.core.balance;

import java.math.BigDecimal;

import com.myucel.account.core.AccountEvent;

public class DepositedEvent implements AccountEvent {

	private final String accountId;
	private final BigDecimal amount;

	public DepositedEvent(String accountId, BigDecimal amount) {
		super();
		this.accountId = accountId;
		this.amount = amount;
	}

	public String getAccountId() {
		return accountId;
	}

	public BigDecimal getAmount() {
		return amount;
	}
}
