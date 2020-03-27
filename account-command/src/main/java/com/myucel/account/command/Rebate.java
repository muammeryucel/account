package com.myucel.account.command;

import java.math.BigDecimal;

import org.axonframework.deadline.annotation.DeadlineHandler;
import org.axonframework.modelling.command.EntityId;

public class Rebate {

	@EntityId
	private String depositId;

	private BigDecimal amount;
	private boolean expired;

	public Rebate(String depositId, BigDecimal amount) {
		super();
		this.depositId = depositId;
		this.amount = amount;
	}

	public String getDepositId() {
		return depositId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public boolean isExpired() {
		return expired;
	}

	@DeadlineHandler(deadlineName = Account.REBATE_EXPIRATION_DEADLINE)
	public void handleActivationExpiration(String depositId) {
		if (this.depositId.compareTo(depositId) == 0) {
			expired = true;
		}
	}
}
