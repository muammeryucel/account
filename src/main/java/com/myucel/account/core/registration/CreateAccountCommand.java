package com.myucel.account.core.registration;

import java.math.BigDecimal;
import java.util.UUID;

import com.myucel.account.core.BaseCommand;

public class CreateAccountCommand extends BaseCommand {

	private final String phoneNumber;
	private final BigDecimal initialBalance;

	public CreateAccountCommand(String phoneNumber, BigDecimal initialBalance) {
		super(UUID.randomUUID().toString());
		this.phoneNumber = phoneNumber;
		this.initialBalance = initialBalance;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public BigDecimal getInitialBalance() {
		return initialBalance;
	}
}
