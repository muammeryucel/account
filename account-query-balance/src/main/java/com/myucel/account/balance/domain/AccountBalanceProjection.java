package com.myucel.account.balance.domain;

import java.math.BigDecimal;

public class AccountBalanceProjection {

	private String accountId;
	private BigDecimal balance;
	private Long version;

	public AccountBalanceProjection(String accountId, BigDecimal balance, Long version) {
		super();
		this.accountId = accountId;
		this.balance = balance;
		this.version = version;
	}

	public String getAccountId() {
		return accountId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public Long getVersion() {
		return version;
	}
}
