package com.myucel.account.balance.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.myucel.account.balance.service.AccountBalanceDTO;
import com.myucel.account.balance.service.AccountBalanceService;

@RestController
public class AccountBalanceController {

	private final AccountBalanceService service;

	public AccountBalanceController(AccountBalanceService service) {
		super();
		this.service = service;
	}

	@GetMapping("/balances/{accountId}")
	public ResponseEntity<AccountBalanceDTO> getAccountBalance(@PathVariable("accountId") String accountId) {
		return ResponseEntity.ok(service.getAccountBalance(accountId));
	}

	@GetMapping("/balances")
	public ResponseEntity<List<AccountBalanceDTO>> getAccountBalances() {
		return ResponseEntity.ok(service.getAccountBalances());
	}
}
