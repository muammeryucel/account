package com.myucel.account.balance.service;

import java.math.BigDecimal;

import com.myucel.account.balance.domain.AccountBalance;

public class AccountBalanceDTO {

	private final String accountId;
	private final BigDecimal balance;

	public static AccountBalanceDTO of(AccountBalance entity) {
		return entity == null ? null : new AccountBalanceDTO(entity);
	}

	private AccountBalanceDTO(AccountBalance entity) {
		accountId = entity.getAccountId();
		balance = entity.getBalance();
	}

	public String getAccountId() {
		return accountId;
	}

	public BigDecimal getBalance() {
		return balance;
	}
}
