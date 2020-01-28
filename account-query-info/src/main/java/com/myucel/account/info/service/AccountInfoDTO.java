package com.myucel.account.info.service;

import java.math.BigDecimal;
import java.util.Date;

import com.myucel.account.info.domain.AccountInfo;

public class AccountInfoDTO {

	private final String accountId;
	private final BigDecimal initialBalance;
	private final String phoneNumber;
	private final String status;
	private final Date creationDate;
	private final Date activationDate;
	private final Date expirationDate;

	public static AccountInfoDTO of(AccountInfo entity) {
		return entity == null ? null : new AccountInfoDTO(entity);
	}

	private AccountInfoDTO(AccountInfo entity) {
		accountId = entity.getAccountId();
		initialBalance = entity.getInitialBalance();
		phoneNumber = entity.getPhoneNumber();
		status = entity.getStatus();
		creationDate = entity.getCreationDate();
		activationDate = entity.getActivationDate();
		expirationDate = entity.getExpirationDate();
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
