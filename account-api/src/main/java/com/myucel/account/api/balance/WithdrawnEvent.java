package com.myucel.account.api.balance;

import java.math.BigDecimal;

import com.myucel.account.api.AccountEvent;

public class WithdrawnEvent implements AccountEvent {

	private final String accountId;
	private final BigDecimal amount;

	public WithdrawnEvent(String accountId, BigDecimal amount) {
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
