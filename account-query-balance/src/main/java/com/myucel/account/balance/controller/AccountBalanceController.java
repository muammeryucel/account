package com.myucel.account.balance.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.myucel.account.balance.domain.AccountBalanceProjection;
import com.myucel.account.balance.service.AccountBalanceService;

@RestController
public class AccountBalanceController {

	private final AccountBalanceService service;

	public AccountBalanceController(AccountBalanceService service) {
		super();
		this.service = service;
	}

	@GetMapping("/balances/{accountId}")
	public CompletableFuture<AccountBalanceProjection> getAccountBalance(@PathVariable("accountId") String accountId) {
		return service.getAccountBalance(accountId);
	}

	@GetMapping("/balances")
	public CompletableFuture<List<AccountBalanceProjection>> getAccountBalances() {
		return service.getAccountBalances();
	}
}
