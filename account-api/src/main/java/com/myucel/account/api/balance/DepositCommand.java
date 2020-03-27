package com.myucel.account.api.balance;

import java.math.BigDecimal;
import java.util.UUID;

import com.myucel.account.api.VersionAwareCommand;

public class DepositCommand extends VersionAwareCommand {

	private final String depositId;
	private final BigDecimal amount;

	public DepositCommand(String accountId, Long version, BigDecimal amount) {
		super(accountId, version);
		this.depositId = UUID.randomUUID().toString();
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
	public String getDepositId() {
		return depositId;
	}
}
