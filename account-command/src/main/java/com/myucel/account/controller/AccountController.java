package com.myucel.account.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myucel.account.service.AccountService;

@RestController
public class AccountController {

	private final AccountService service;

	public AccountController(AccountService service) {
		super();
		this.service = service;
	}

	@PostMapping("/accounts")
	public ResponseEntity<?> createAccount(@RequestParam("phone") String phoneNumber,
			@RequestParam("balance") BigDecimal initialBalance) {
		String accountId = service.createAccount(phoneNumber, initialBalance);
		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{accountId}")
				.buildAndExpand(accountId).toUri()).build();
	}

	@PostMapping("/activate/{phone}")
	public ResponseEntity<?> activateAccount(@PathVariable("phone") String phoneNumber,
			@RequestParam("activation-code") String activationCode) {
		service.activateAccount(phoneNumber, activationCode);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/transfer/{from}")
	public ResponseEntity<?> transfer(@PathVariable("from") String senderId, @RequestParam("to") String recipientId,
			@RequestParam("amount") BigDecimal amount) {
		service.transfer(senderId, recipientId, amount);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/deposit/{accountId}/{version}")
	public ResponseEntity<?> deposit(@PathVariable("accountId") String accountId, @PathVariable("version") Long version,
			@RequestParam("amount") BigDecimal amount) {
		service.deposit(accountId, version, amount);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/withdraw/{accountId}/{version}")
	public ResponseEntity<?> withdraw(@PathVariable("accountId") String accountId,
			@PathVariable("version") Long version, @RequestParam("amount") BigDecimal amount) {
		service.withdraw(accountId, version, amount);
		return ResponseEntity.ok().build();
	}
}
