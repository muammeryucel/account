package com.myucel.account.api;

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
