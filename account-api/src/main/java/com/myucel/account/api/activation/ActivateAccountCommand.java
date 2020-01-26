package com.myucel.account.api.activation;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.myucel.account.api.BaseCommand;

public class ActivateAccountCommand extends BaseCommand {

	public ActivateAccountCommand(String accountId) {
		super(accountId);
	}
}
