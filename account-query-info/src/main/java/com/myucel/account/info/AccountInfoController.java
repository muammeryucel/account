package com.myucel.account.info;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountInfoController {

	private final AccountInfoService service;

	public AccountInfoController(AccountInfoService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/accounts")
	public ResponseEntity<List<AccountInfoDTO>> getAccounts() {
		return ResponseEntity.ok(service.getAccounts());
	}
}
