package com.myucel.account.info.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account_info")
public class AccountInfo {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "account_id")
	private String accountId;

	private String status;

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
		this.status = "Waiting for activation";
	}

	public String getAccountId() {
		return accountId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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
