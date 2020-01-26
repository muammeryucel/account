package com.myucel.account.query.info;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.myucel.account.command.AccountStatus;
import com.myucel.account.core.BaseEntity;

@Entity
@Table(name = "account_info")
public class AccountInfo extends BaseEntity {

	@Column(name = "account_id")
	private String accountId;

	@Enumerated(EnumType.STRING)
	private AccountStatus status;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "initial_balance")
	private BigDecimal initialBalance;

	@Column(name = "creation_date")
	private Date creationDate;

	@Column(name = "activation_date")
	private Date activationDate;

	@Column(name = "expiration_date")
	private Date expirationDate;

	public AccountInfo() {
		// Required by JPA
	}

	public AccountInfo(String accountId, String phoneNumber, BigDecimal initialBalance) {
		this.accountId = accountId;
		this.phoneNumber = phoneNumber;
		this.initialBalance = initialBalance;
		this.status = AccountStatus.ACTIVATION_REQUIRED;
	}

	public String getAccountId() {
		return accountId;
	}

	public AccountStatus getStatus() {
		return status;
	}

	public void setStatus(AccountStatus status) {
		this.status = status;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public BigDecimal getInitialBalance() {
		return initialBalance;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
}
