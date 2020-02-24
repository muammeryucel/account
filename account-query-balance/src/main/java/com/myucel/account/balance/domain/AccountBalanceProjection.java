package com.myucel.account.balance.domain;

import java.math.BigDecimal;

public interface AccountBalanceProjection {

	String getAccountId();

	BigDecimal getBalance();

	Long getVersion();
}
