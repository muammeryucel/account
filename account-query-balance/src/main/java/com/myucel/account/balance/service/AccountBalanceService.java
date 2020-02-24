package com.myucel.account.balance.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myucel.account.balance.domain.AccountBalanceRepository;
import com.myucel.account.balance.domain.AccountBalanceProjection;

@Service
@Transactional(readOnly = true)
public class AccountBalanceService {

	private final AccountBalanceRepository repository;

	public AccountBalanceService(AccountBalanceRepository repository) {
		super();
		this.repository = repository;
	}

	public List<AccountBalanceProjection> getAccountBalances() {
		return repository.findProjectionBy();
	}

	public AccountBalanceProjection getAccountBalance(String accountId) {
		return repository.findProjectionByAccountId(accountId);
	}
}
