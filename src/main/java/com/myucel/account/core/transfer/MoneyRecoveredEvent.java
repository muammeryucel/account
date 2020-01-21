package com.myucel.account.core.transfer;

import java.math.BigDecimal;

public class MoneyRecoveredEvent {

	private final String senderId;
	private final BigDecimal amount;

	public MoneyRecoveredEvent(String senderId, BigDecimal amount) {
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
