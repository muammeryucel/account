package com.myucel.account.core.exception;

import com.myucel.account.command.AccountStatus;

public class UnexpectedAccountStatusException extends IllegalStateException {

	private String accountId;
	private AccountStatus actualStatus;
	private AccountStatus expectedStatus;

	public UnexpectedAccountStatusException(String accountId, AccountStatus actualStatus,
			AccountStatus expectedStatus) {
		super(accountId + "Unexpected status for account " + accountId + ". Actual Status: " + actualStatus
				+ ", Expected Status: " + expectedStatus);
		this.accountId = accountId;
		this.actualStatus = actualStatus;
		this.expectedStatus = expectedStatus;
	}

	public String getAccountId() {
		return accountId;
	}

	public AccountStatus getActualStatus() {
		return actualStatus;
	}

	public AccountStatus getExpectedStatus() {
		return expectedStatus;
	}
}
