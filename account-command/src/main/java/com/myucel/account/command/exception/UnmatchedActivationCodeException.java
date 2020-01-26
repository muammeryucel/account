package com.myucel.account.command.exception;

public class UnmatchedActivationCodeException extends IllegalStateException {

	private String accountId;

	public UnmatchedActivationCodeException(String accountId) {
		super("Wrong activation code entered for account " + accountId);
		this.accountId = accountId;
	}

	public String getAccountId() {
		return accountId;
	}
}
