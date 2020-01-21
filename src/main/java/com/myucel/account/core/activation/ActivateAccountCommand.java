package com.myucel.account.core.activation;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.myucel.account.core.BaseCommand;

public class ActivateAccountCommand extends BaseCommand {

	public ActivateAccountCommand(String accountId) {
		super(accountId);
	}
}
