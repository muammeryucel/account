package com.myucel.account.balance;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AccountBalanceService {

	private final AccountBalanceRepository repository;

	public AccountBalanceService(AccountBalanceRepository repository) {
		super();
		this.repository = repository;
	}

	public List<AccountBalanceDTO> getAccountBalances() {
		return repository.findAll().stream().map(AccountBalanceDTO::of).collect(Collectors.toList());
	}

	public AccountBalanceDTO getAccountBalance(String accountId) {
		return AccountBalanceDTO.of(repository.findByAccountId(accountId));
	}
}