package com.myucel.account.info;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AccountInfoService {

	private final AccountInfoRepository repository;

	public AccountInfoService(com.myucel.account.info.AccountInfoRepository repository) {
		super();
		this.repository = repository;
	}

	public List<AccountInfoDTO> getAccounts() {
		return repository.findAll().stream().map(AccountInfoDTO::of).collect(Collectors.toList());
	}
}
