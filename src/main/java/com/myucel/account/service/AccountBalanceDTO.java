package com.myucel.account.service;

import java.math.BigDecimal;

import com.myucel.account.query.balance.AccountBalance;

public class AccountBalanceDTO {

	private String accountId;
	private BigDecimal balance;

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
