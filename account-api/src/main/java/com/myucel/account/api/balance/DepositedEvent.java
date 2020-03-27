package com.myucel.account.api.balance;

import java.math.BigDecimal;

import com.myucel.account.api.AccountEvent;

public class DepositedEvent implements AccountEvent {

	private final String accountId;
	private final String depositId;
	private final BigDecimal amount;

	public DepositedEvent(String accountId, String depositId, BigDecimal amount) {
		super();
		this.accountId = accountId;
		this.depositId = depositId;
		this.amount = amount;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getDepositId() {
		return depositId;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
}
