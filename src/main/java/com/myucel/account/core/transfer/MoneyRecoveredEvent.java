package com.myucel.account.core.transfer;

import java.math.BigDecimal;

import com.myucel.account.core.AccountEvent;

public class MoneyRecoveredEvent implements AccountEvent {

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
