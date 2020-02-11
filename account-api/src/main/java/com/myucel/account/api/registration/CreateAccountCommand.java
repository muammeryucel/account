package com.myucel.account.api.registration;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.myucel.account.api.BaseCommand;

public class CreateAccountCommand extends BaseCommand {

	private static final AtomicInteger ID_PROVIDER = new AtomicInteger(0);

	private final String phoneNumber;
	private final BigDecimal initialBalance;

	public CreateAccountCommand(String phoneNumber, BigDecimal initialBalance) {
		//super(String.valueOf(ID_PROVIDER.incrementAndGet()));
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
