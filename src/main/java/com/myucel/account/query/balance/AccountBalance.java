package com.myucel.account.query.balance;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.myucel.account.core.BaseEntity;

@Entity
@Table(name = "account_balance")
public class AccountBalance extends BaseEntity {

	@Column(name = "account_id")
	private String accountId;

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

	void add(BigDecimal amount) {
		balance = balance.add(amount);
	}

	void subtract(BigDecimal amount) {
		balance = balance.subtract(amount);
	}
}
