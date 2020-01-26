package com.myucel.account.balance;

import java.math.BigDecimal;

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
