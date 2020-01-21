package com.myucel.account.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myucel.account.service.AccountBalanceDTO;
import com.myucel.account.service.AccountBalanceService;
import com.myucel.account.service.AccountService;

@RestController
public class AccountController {

	private final AccountService accountService;
	private final AccountBalanceService accountBalanceService;

	public AccountController(AccountService accountService, AccountBalanceService accountBalanceService) {
		super();
		this.accountService = accountService;
		this.accountBalanceService = accountBalanceService;
	}

	@PostMapping("/accounts")
	public ResponseEntity<?> createAccount(@RequestParam("phone") String phoneNumber, @RequestParam("balance") BigDecimal initialBalance) {
		String accountId = accountService.createAccount(phoneNumber, initialBalance);
		return ResponseEntity.created(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{accountId}").buildAndExpand(accountId).toUri())
				.build();
	}

	@PostMapping("/activate/{accountId}")
	public ResponseEntity<?> activateAccount(@PathVariable("accountId") String accountId, @RequestParam("activation-code") String activationCode) {
		accountService.activateAccount(accountId, activationCode);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/transfer/{from}")
	public ResponseEntity<?> transfer(@PathVariable("from") String senderId, @RequestParam("to") String recipientId,
			@RequestParam("amount") BigDecimal amount) {
		accountService.transfer(senderId, recipientId, amount);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/accounts/{accountId}")
	public ResponseEntity<AccountBalanceDTO> getAccountBalance(@PathVariable("accountId") String accountId) {
		return ResponseEntity.ok(accountBalanceService.getAccountBalance(accountId));
	}

	@GetMapping("/accounts")
	public ResponseEntity<List<AccountBalanceDTO>> getAccountBalances() {
		return ResponseEntity.ok(accountBalanceService.getAccountBalances());
	}
}
