package com.myucel.account.api.transfer;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ReceiveMoneyCommad {

	@TargetAggregateIdentifier
	private final String recipientId;
	
	private final String senderId;
	private final BigDecimal amount;

	public ReceiveMoneyCommad(String recipientId, String senderId, BigDecimal amount) {
		super();
		this.senderId = senderId;
		this.recipientId = recipientId;
		this.amount = amount;
	}

	public String getSenderId() {
		return senderId;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public BigDecimal getAmount() {
		return amount;
	}
}
