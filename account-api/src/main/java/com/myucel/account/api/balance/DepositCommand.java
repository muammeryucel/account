package com.myucel.account.api.balance;

import java.math.BigDecimal;

import com.myucel.account.api.VersionAwareCommand;

public class DepositCommand extends VersionAwareCommand {

	private final BigDecimal amount;

	public DepositCommand(String accountId, Long version, BigDecimal amount) {
		super(accountId, version);
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}
}
