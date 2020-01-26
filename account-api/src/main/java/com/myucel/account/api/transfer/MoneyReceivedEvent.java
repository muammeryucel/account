package com.myucel.account.api.transfer;

import java.math.BigDecimal;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.myucel.account.api.AccountEvent;

public class MoneyReceivedEvent implements AccountEvent {

	private final String senderId;
	private final String recipientId;
	private final BigDecimal amount;

	public MoneyReceivedEvent(String recipientId, String senderId, BigDecimal amount) {
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
