package com.myucel.account.info.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myucel.account.info.domain.AccountInfoProjection;
import com.myucel.account.info.service.AccountInfoService;

@RestController
public class AccountInfoController {

	private final AccountInfoService service;

	public AccountInfoController(AccountInfoService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/accounts")
	public CompletableFuture<List<AccountInfoProjection>> getAccounts() {
		return service.getAccounts();
	}
}
