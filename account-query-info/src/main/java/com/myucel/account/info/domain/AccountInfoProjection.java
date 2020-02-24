package com.myucel.account.info.domain;

import java.math.BigDecimal;
import java.util.Date;

public class AccountInfoProjection {

	private final String accountId;
	private final BigDecimal initialBalance;
	private final String phoneNumber;
	private final String status;
	private final Date creationDate;
	private final Date activationDate;
	private final Date expirationDate;

	public AccountInfoProjection(String accountId, BigDecimal initialBalance, String phoneNumber, String status,
			Date creationDate, Date activationDate, Date expirationDate) {
		super();
		this.accountId = accountId;
		this.initialBalance = initialBalance;
		this.phoneNumber = phoneNumber;
		this.status = status;
		this.creationDate = creationDate;
		this.activationDate = activationDate;
		this.expirationDate = expirationDate;
	}

	public String getAccountId() {
		return accountId;
	}

	public BigDecimal getInitialBalance() {
		return initialBalance;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getStatus() {
		return status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}
}
