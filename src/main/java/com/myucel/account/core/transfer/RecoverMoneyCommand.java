package com.myucel.account.core.transfer;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class RecoverMoneyCommand {

	@TargetAggregateIdentifier
	private final String senderId;
	
	private final BigDecimal amount;

	public RecoverMoneyCommand(String senderId, BigDecimal amount) {
		super();
		this.senderId = senderId;
		this.amount = amount;
	}

	public String getSenderId() {
		return senderId;
	}

	public BigDecimal getAmount() {
		return amount;
	}
}
