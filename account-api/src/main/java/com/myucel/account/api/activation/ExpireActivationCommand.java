package com.myucel.account.api.activation;

import com.myucel.account.api.BaseCommand;

public class ExpireActivationCommand extends BaseCommand {

	public ExpireActivationCommand(String accountId) {
		super(accountId);
	}
}
