package com.myucel.account.core;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class BaseCommand {

	@TargetAggregateIdentifier
	private final String accountId;

	public BaseCommand(String accountId) {
		super();
		this.accountId = accountId;
	}
	
	public String getAccountId() {
		return accountId;
	}
}
