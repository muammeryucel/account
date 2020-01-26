package com.myucel.account.balance;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account_balance")
public class AccountBalance {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "account_id")
	private String accountId;

	private Long version;

	private BigDecimal balance;

	public AccountBalance() {
		// Required by JPA
	}

	public AccountBalance(String accountId, BigDecimal initialBalance) {
		this.accountId = accountId;
		this.balance = initialBalance;
	}

	public String getAccountId() {
		return accountId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	void addToBalance(BigDecimal amount) {
		balance = balance.add(amount);
	}

	void subtractFromBalance(BigDecimal amount) {
		balance = balance.subtract(amount);
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
