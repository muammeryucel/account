package com.myucel.account.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.myucel.account.service.AccountBalanceDTO;
import com.myucel.account.service.AccountInfoDTO;
import com.myucel.account.service.AccountQueryService;

@RestController
public class AccountQueryController {

	private final AccountQueryService service;

	public AccountQueryController(AccountQueryService service) {
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
	
	@GetMapping("/accounts")
	public ResponseEntity<List<AccountInfoDTO>> getAccounts() {
		return ResponseEntity.ok(service.getAccounts());
	}
}
