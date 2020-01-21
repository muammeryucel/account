package com.myucel.account.core.transfer;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class MoneySentEvent {

	private final String senderId;
	private final String recipientId;
	private final BigDecimal amount;

	public MoneySentEvent(String senderId, String recipientId, BigDecimal amount) {
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
